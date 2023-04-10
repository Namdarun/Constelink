import React, { useRef, useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import styles from "./FundRegister.module.css";
import Select from "react-select";
import SunEditor from 'suneditor-react';
import SunEditorCore from "suneditor/src/lib/core";
import 'suneditor/dist/css/suneditor.min.css';
import DatePicker, { registerLocale } from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { getYear, getMonth } from "date-fns"
import ko from 'date-fns/locale/ko' // 한국어 적용
import axios from "axios";

import Loading from "../assets/img/mining.gif";

import Web3 from "web3";
import { AbiItem } from 'web3-utils';
import { FUND_ABI } from "../web3js/FUND_ABI";
import { TransactionConfig } from 'web3-core';
import { TransactionReceipt } from 'web3-core/types';



const titleRegexp = /^[가-힣 ]{1,20}$/; // 공백포함 한글 1~20자
const goalRegexp = /^[0-9]{1,10}$/; // 숫자만 가능
const imageRegexp = /(.*?)\.(jpg|jpeg|png)$/; // 확장자는 jpg, jpeg, png
const maxSize = 50 * 1024 * 1024; registerLocale("ko", ko); // 한국어 적용
const _ = require('lodash');


const MM_KEY = process.env.REACT_APP_MM_PRIVATE_KEY;
const FUND_CA = "0x07A8A469ca0D02049599874580a0aBA76dd34F18";
// const TEST_PUB_FUND_CA = "0x962aDFA41aeEb2Dc42E04586dBa143f2404FD10D";

// const A_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMSIsImlhdCI6MTY4MDc0OTE0NCwiZXhwIjoxNjgwNzUwOTQ0LCJyb2xlIjoiSE9TUElUQUwifQ.L5lE9Qn3SMkQXMkFo4vWm-rOQrspFoDWJKbD-WpR5FYBgfqMjqncT29Swsy3MIfoOIJs-dcCXPvWeFqk4HfXsA";

interface category {
  id: number,
  categoryName: string
}

interface transactionArgs {
  id: number,
  total: number,
  time: number
}


const FundRegister: React.FC = () => {

  const A_TOKEN = localStorage.getItem("access_token");

  // 현재 접속한 유저의 metamask address 가져오기
  const [web3, setWeb3] = useState<Web3 | null>(null);
  const [address, setAddress] = useState<string | null>(null);
  const [contract, setContract] = useState<any | null>(null);

  // 1. 계정 주소 불러오고, 펀딩 컨트랙트 연결
  const [web3Success, setWeb3Success] = useState(false);
  useEffect(() => {
    const detectWeb3 = async () => {
      // If MetaMask is installed
      if (typeof window.ethereum !== "undefined") {
        // create an web3 instance
        const provider = window.ethereum;
        await provider.request({ method: "eth_requestAccounts" });
        const web3Instance = new Web3(provider);
        setWeb3(web3Instance);

        // Get the user's address
        const accounts = await web3Instance.eth.getAccounts();
        setAddress(accounts[0]);

        // Load the contract
        const contractInstance = new web3Instance.eth.Contract(FUND_ABI as AbiItem[], FUND_CA);
        setContract(contractInstance);
      }
    };
    detectWeb3();
    console.log('1. 메타마스크 연결 성공');
    setWeb3Success(true);
  }, []);

  // 2. 모금시작하기
  const [isDone, setIsDone] = useState(false);
  async function sendTransactionStartFunding(id: number, total: number, time: number) {
    if (web3) {
      const master = web3.eth.accounts.privateKeyToAccount(MM_KEY!);
      const txParams: TransactionConfig = {

        from: master.address,
        to: FUND_CA,
        gas: 1000000,
        data: contract.methods.startFund(id, total, time, address).encodeABI(),
        // data: contract.methods.startFund(1, 1111116, 2222229, address).encodeABI(),
        nonce: await web3.eth.getTransactionCount(master.address),
        chainId: 11155111,
      };

      const signedTX = await master.signTransaction(txParams);
      // console.log('이게 signedTX');
      // console.log(signedTX.rawTransaction);
      // console.log('입니다');

      const receipt: TransactionReceipt = await web3.eth.sendSignedTransaction(signedTX.rawTransaction!);
      console.log('id: ', id);
      console.log(`Transaction hash: ${receipt.transactionHash}`);
      setIsDone(true);
    } else {
      console.log('Web3 is not available');
    };
  }




  const navigate = useNavigate();
  const location = useLocation();
  const state = location.state;

  if (state) {
    console.log(state);
  }


  // 에러 메시지
  const [imgErr, setImgErr] = useState(false);
  const [imgErrMsg, setImgErrMsg] = useState('');
  const [titleErr, setTitleErr] = useState(false);
  const [titleErrMsg, setTitleErrMsg] = useState('');
  const [goalErr, setGoalErr] = useState(false);
  const [goalErrMsg, setGoalErrMsg] = useState('');
  const [noValErr, setNoValErr] = useState(false);

  // const hospitalId = 21;

  // 페이지 로딩하면서 수혜자 리스트 불러오기
  const [benList, setBenList] = useState<object[]>([]);
  // 수혜자 리스트 axios요청 보내기
  const getBenList = async () => {

    await axios
      .get(`/beneficiary/beneficiaries/hospital/self`,
        {
          params: { page: 1, size: 50000000 },
          headers: {
            Authorization: A_TOKEN
          }
        })
      .then((res) => {
        // console.log(res);
        res.data.content.map((ben: any) => {
          return setBenList(benList => [...benList, {
            value: ben.beneficiaryId,
            label: ben.beneficiaryName,
            maxGoal: ben.beneficiaryAmountGoal - ben.beneficiaryAmountRaised
          }])
        })
      })
      .catch((err) => {
        console.log(err);
      })
  };

  // 페이지 로딩하면서 카테고리 리스트 불러오기
  const [cateList, setCateList] = useState<object[]>([]);
  // 카테고리 리스트 axios요청 보내기
  const getCateList = async () => {

    await axios
      .get('/fundraising/categories?page=1&size=5&sortBy=ALL')
      .then((res) => {
        res.data.content.map((category: category) => {
          return setCateList(cateList => [...cateList, {
            value: category.id,
            label: category.categoryName
          }])
        })
      })
      .catch((err) => {
        console.log(err);
      })
  }

  // 첫 런데링 시 수혜자, 카테고리 리스트 불러오기
  useEffect(() => {
    getBenList();
    getCateList();
  }, []);


  // 수혜자 설정
  const [benId, setBenId] = useState('');
  const handleBen = (e: any) => {
    // console.log(e);
    setBenId(e.value);
    // 모금액 상한 설정
    setMaxGoal(e.maxGoal);
  }

  // 썸네일 설정
  const [imgPreUrl, setImgPreUrl] = useState('');
  const [image, setImage] = useState(null);
  const handleImage = (e: any) => {
    const file = e.target.files[0];
    // 사진 확장자 검사
    if (!imageRegexp.test(file.name)) {
      setImgErr(true);
      setImgErrMsg('사진 파일을 올려주세요.');
      return;
    }
    // 사진 용량 검사
    if (file.size > maxSize) {
      setImgErr(true);
      setImgErrMsg('사진 용량이 50MB를 초과했습니다.');
      return;
    } else {
      setImgErr(false);
      setImgErrMsg('');
      setImage(file);
      setImgPreUrl(URL.createObjectURL(file));
    }
  };

  // 카테고리 설정
  const [cate, setCate] = useState('');
  const handleCate = (e: any) => {
    setCate(e.value);
    // console.log(e.value);
  }

  // 제목 설정
  const [title, setTitle] = useState('');
  const handleTitle = (e: any) => {
    const title = e.target.value;
    if (!titleRegexp.test(title)) {
      setTitleErr(true);
      setTitleErrMsg('제목은 공백포함 한글로 20자까지 입력가능')
    } else {
      setTitleErr(false);
      setTitleErrMsg('');
      setTitle(title);
    }
  };

  // 모금액 상한 설정
  const [maxGoal, setMaxGoal] = useState(0);

  // 목표금액 설정
  const [goal, setGoal] = useState(0);
  const handleGoal = (e: any) => {
    const goale = e.target.value;
    // setGoal(goale);
    // console.log(goal);
    setCheckGoal(`${goale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원`);
    if (goale < 1 || goale > maxGoal) {
      setGoalErr(true);
      setGoalErrMsg(`최대금액 = ${maxGoal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원`);
    } else if (!goalRegexp.test(goale)) {
      setGoalErr(true);
      setGoalErrMsg(`숫자만 입력가능`);
    } else {
      setGoalErr(false);
      setGoalErrMsg('');
      // 금액띄워주기
      setCheckGoal(`${goale.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원`);
      setGoal(Number(goale));
    }
  };

  // 확인용 목표금액 설정
  const [checkGoal, setCheckGoal] = useState('')

  // 사연 입력받기

  const contentChangeHandler = (e: string) => {
    // console.log(e);
    setContent(e);
  }

  const editor = useRef<SunEditorCore>();
  const [content, setContent] = useState('');
  // The sunEditor parameter will be set to the core sundeitor instance when this function is called
  const getSunEditorInstance = (sunEditor: SunEditorCore) => {
    editor.current = sunEditor;
    // setContent("a");
    // console.log(editor.current);
  }

  // 사용처 리스트 

  // 마감 일시 설정
  const [endTime, setEndTime] = useState(new Date());
  const today = new Date()
  const tomorrow = new Date(today.setDate(today.getDate() + 1));
  // 마감 선택 범위 설정
  const years = _.range(getYear(new Date()) + 1, 2140, 1);
  const months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

  // 사진 Url 백에서 가져오기
  const [imgUrl, setImgUrl] = useState('');
  const getImgUrI = async () => {
    const formData = new FormData();
    if (image) {
      formData.append("file", image);
    };

    await axios
      .post('/files/saveimg', formData, {
        headers: {
          Authorization: A_TOKEN
        }
      })
      .then((res) => {
        console.log('1. 사진 받아왔음');
        setImgUrl(res.data.fileUrl);
      })
      .catch((err) => {
        console.log(err);
      })
  }

  // POST 요청 ~ 블록체인 등록까지 로딩표시하기
  const [isLoading, setIsLoading] = useState(false);

  // POST 요청 내에서 트랜젝션 args받아오기
  const [args, setArgs] = useState<transactionArgs>();

  // POST 요청 보내기
  // 데이터 보냈다고 알려주고 transaction 시작
  const [isPosted, setIsPosted] = useState(false);
  const sendPost = async () => {

    // 동기처리 실패로 값없으면 끝내버리기
    if (!benId || !cate || !goal || !endTime || !title || !content || !imgUrl) {
      return
    }

    setIsLoading(true);

    const funding = {
      beneficiaryId: Number(benId),
      categoryId: Number(cate),
      fundraisingAmountGoal: goal,
      fundraisingEndTime: endTime.getTime(),
      fundraisingTitle: title,
      fundraisingStory: content,
      fundraisingThumbnail: imgUrl,
      fundraisingWillUse: '입원비/수술비/의약품'
    }


    await axios
      .post('/fundraising/fundraisings/register', funding, {
        headers: {
          Authorization: A_TOKEN
        }
      })
      .then((res) => {
        console.log('2. 포스트 요청 보냈음');

        let now = new Date().getTime()

        setArgs({
          id: res.data.id,
          total: funding.fundraisingAmountGoal,
          time: Math.floor((funding.fundraisingEndTime - now) / 1000),
        });
        setIsPosted(true);
      })
      .catch((err) => {
        console.log(err);
      })
  };

  // 백에 이미지 보낸거 확인하면 POST요청 보내기
  useEffect(() => {
    sendPost();
  }, [imgUrl])


  // POST 요청 성공 확인하면 transaction 보내기 => fundId 받아오는거 확인하면 보내면 됨
  useEffect(() => {

    if (isPosted) {
      console.log('3. 트랜젝션 보내기');
      sendTransactionStartFunding(args!.id, args!.total, args!.time);
    }
  }, [isPosted])


  // 할거 없으면 인제 홈가자
  useEffect(() => {


    if (isDone) {
      alert('모금이 시작되었습니다!');
      navigate('/mypage');
    }
  }, [isDone])



  // 요청 보내도 되는지 검사
  const checkValidity = () => {
    if (
      !image || !benId || !cate || !title || !goal || !content || !endTime
    ) {
      setNoValErr(true);
      alert('입력하지 않은 값이 있습니다.');
    } else {
      setNoValErr(false);
    };

    if (
      !imgErr && !titleErr && !goalErr && !noValErr
    ) {
      console.log('보내자');
      getImgUrI();
    };
  };

  return (
    <>
      <div className={styles.mainWrapper}>
        <div className={styles.mainTitle}>모금 시작하기</div>
        {/* 수혜자 선택 */}
        <div className={styles.benWrapper}>
          <div className={styles.inputTitle}>
            수혜자
          </div>
          <div className={styles.selectWrapper}>
            <Select
              options={benList}
              onChange={handleBen}
              placeholder="수혜자 선택"
            />
          </div>
        </div>
        {/* 썸네일 등록 */}
        <div className={styles.imgWrapper}>
          {imgPreUrl ? (
            <div className={styles.divWithImg}>
              <img className={styles.fundImg} src={imgPreUrl} alt="" />
              <label htmlFor="thumbImg">
                <div className={styles.editIcon} />
              </label>
              <input
                type="file"
                id="thumbImg"
                className={styles.inputFile}
                accept="image/jpg, image/png, image/jpeg"
                onChange={handleImage}
              />
            </div>
          ) : (
            <label htmlFor="thumbImg">
              <div className={styles.imgThumb}>
                <div className={styles.thumbText}>
                  썸네일을 등록해 주세요.
                </div>
                <div className={styles.plusIcon} />
                <input
                  type="file"
                  id="thumbImg"
                  className={styles.inputFile}
                  accept="image/jpg, image/png, image/jpeg"
                  onChange={handleImage}
                />
              </div>
            </label>
          )}
          {imgErr && (
            <div className={styles.errMsg}>{imgErrMsg}</div>
          )}
        </div>
        {/* 카테고리 선택 */}
        <div className={styles.categoryWrapper}>
          <div className={styles.inputTitle}>
            카테고리
          </div>
          <div className={styles.selectWrapper}>
            <Select
              options={cateList}
              onChange={handleCate}
              placeholder="카테고리 선택"
            />
          </div>
        </div>
        {/* 제목 입력 */}
        <div className={styles.titleWrapper}>
          <div className={styles.inputTitle}>
            제목
          </div>
          <div className={styles.titleInput}>
            <input
              className={styles.inputBox}
              placeholder="제목을 입력해 주세요"
              onChange={handleTitle}
            />
          </div>
          {/* 제목 에러 메시지 */}
          {titleErr && (
            <div className={styles.errMsg}>{titleErrMsg}</div>
          )}
        </div>
        {/* 모금액 입력 */}
        <div className={styles.fundWrapper}>
          <div className={styles.inputTitle}>
            모금액
          </div>
          <div className={styles.fundInput}>
            <input
              className={styles.inputBox}
              type="text"
              maxLength={10}
              placeholder="모금액을 입력해 주세요"
              onChange={handleGoal}
            />
            {goalErr && (
              <div className={styles.errMsg}>{goalErrMsg}</div>
            )}
          </div>
          {/* 모금액 확인 */}
          <div className={styles.fundCheckInput}>
            {checkGoal ? (
              <div className={styles.fundCheckPlacefiller}>{checkGoal}</div>
            ) : (
              <div className={styles.fundCheckPlaceholder}>모금액을 확인해 주세요</div>
            )}
          </div>
        </div>
        {/* 사연 입력 */}
        <div className={styles.storyWrapper}>
          <div className={styles.storyInputTitle}>
            사연
          </div>
          <SunEditor
            getSunEditorInstance={getSunEditorInstance}
            lang="en"
            width="100%"
            height="25rem"
            autoFocus={false}
            onChange={contentChangeHandler}
            setDefaultStyle="font-family:Hahmlet;font-size: 20px;"
            placeholder="환자의 사연을 적어주세요"
            // onChange={(e)=> console.log(e)
            // }
            setOptions={{
              buttonList: [
                [
                  "bold",
                  "underline",
                  "table",
                  "image",
                  "video",
                  "audio",
                  "italic",
                  "fontSize",
                  "formatBlock",
                  "list",
                  "fontColor"
                ]
              ]
            }}
          />
        </div>
        {/* 치료비 사용처 입력 */}
        {/* <div className={styles.useWrapper}>
          <div className={styles.use_inputTitle}>
            모금 사용처
            <div className={styles.plus_btn} />
          </div>
          <div className={styles.useInput}>
            <input
              className={styles.inputBox}
              placeholder="사용처를 입력해 주세요"
              onChange={handleTitle}
            />
          </div>
          제목 에러 메시지
          {titleErr && (
            <div className={styles.errMsg}>{titleErrMsg}</div>
          )}
        </div> */}

        {/* 모금 종료시간 선택 */}
        <div className={styles.timeWrapper}>
          <div className={styles.timeInputTitle}>
            모금 종료시간
          </div>
          {/* 달력 */}
          <DatePicker
            // dateFormat="yyyy년 MM월 dd일"
            dateFormat="yyyy년 MM월 dd일 hh시 mm분"
            selected={endTime}
            onChange={(t: Date) => setEndTime(t)}
            minDate={tomorrow}
            todayButton={"Today"}
            locale="ko"
            // 시간 선택
            showTimeSelect
            renderCustomHeader={({
              date,
              changeYear,
              changeMonth,
              decreaseMonth,
              increaseMonth,
              prevMonthButtonDisabled,
              nextMonthButtonDisabled,
            }) => (
              <div
                style={{
                  margin: 10,
                  display: "flex",
                  justifyContent: "center",
                }}
              >
                <button
                  onClick={decreaseMonth}
                  disabled={prevMonthButtonDisabled}
                >{"<"}</button>
                <select
                  value={getYear(date)}
                  onChange={({ target: { value } }) =>
                    changeYear(Number(value))}
                >
                  {years.map((option: number) => (
                    <option key={option} value={option}>
                      {option}
                    </option>
                  ))}
                </select>

                <select
                  value={months[getMonth(date)]}
                  onChange={({ target: { value } }) =>
                    changeMonth(months.indexOf(value))
                  }
                >
                  {months.map((option) => (
                    <option key={option} value={option}>
                      {option}
                    </option>
                  ))}
                </select>
                <button
                  onClick={increaseMonth}
                  disabled={nextMonthButtonDisabled}
                >
                  {">"}
                </button>
              </div>
            )}
          />
        </div>
        {isLoading ? (
          <div className={styles.btnsWrapper}>
            <div className={styles.btnLoading}>
              <img className={styles.imgLoading} src={Loading} alt="로딩중" />
            </div>
          </div>
        ) : (
          <div className={styles.btnsWrapper}>
            <div
              className={styles.btnCancle}
            // onClick={forCheck}
            >취소</div>
            <div
              className={styles.btnRegister}
              onClick={checkValidity}
            >등록하기</div>
          </div>
        )}
      </div>
    </>
  )
}

export default FundRegister;
