import React, {useEffect, useState} from "react";
import { useNavigate } from "react-router-dom";
import DatePicker, { registerLocale } from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { getYear, getMonth } from "date-fns"
import ko from 'date-fns/locale/ko' // 한국어 적용
import styles from "./BenRegister.module.css";
import axios from "axios";

import Loading from "../assets/img/mining.gif";


const nameRegexp = /^[가-힣]{1,5}$/; // 한글 1~5자
const diseaseKorRegexp = /^[가-힣 ]{1,18}$/; // 공백포함 한글 1~18자
const diseaseEngRegexp = /^[a-zA-Z ]{2,36}$/; // 공백포함 영문 2~36자
const goalRegexp = /^[0-9]{1,10}$/; // 숫자만 가능
const imageRegexp = /(.*?)\.(jpg|jpeg|png)$/; // 확장자는 jpg, jpeg, png
const maxSize = 50 * 1024 * 1024; // 사진파일크기 50mb
registerLocale("ko", ko); // 한국어 적용
const _ = require('lodash');


const BenRegister: React.FC = () => {
  
  const accessToken = localStorage.getItem("access_token");
  const navigate = useNavigate();

  // 에러 설정
  const [imgErr, setImgErr] = useState(false);
  const [imgErrMsg, setImgErrMsg] = useState('');
  const [nameErr, setNameErr] = useState(false);
  const [nameErrMsg, setNameErrMsg] = useState('');
  const [diseaseErr, setDiseaseErr] = useState(false);
  const [diseaseErrMsg, setDiseaseErrMsg] = useState('');
  const [goalErr, setGoalErr] = useState(false);
  const [goalErrMsg, setGoalErrMsg] = useState('');
  // 금액 확인 메시지
  const [goalCheck, setGoalCheck] = useState(false);
  const [goalCheckMsg, setGoalCheckMsg] = useState('')
  // 값입력 안되었을 경우 에러
  const [noValErr, setNoValErr] = useState(false);
  // const [noValErrMsg, setNoValErrMsg] = useState('');

  // 병원 ID
  // 추후에 상태관리로 병원 로그인시 들고다님
  // const hospitalId = 21;

  // 사진 설정
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

  // 이름 설정
  const [benName, setBenName] = useState('');
  const handleBenName = (e: any) => {
    const name = e.target.value.slice(0, 5);
    if (!nameRegexp.test(name)) {
      setNameErr(true);
      setNameErrMsg('이름은 한글 5자 까지 입력가능');
    } else {
      setNameErr(false);
      setNameErrMsg('');
      setBenName(name);
    }
  };

  // 생일 설정
  const [birthDate, setBirthDate] = useState(new Date());
  const today = new Date()
  // 생일 선택 범위 설정
  const years = _.range(1900, getYear(new Date()) + 1, 1);
  const months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

  // 병명 설정
  const [diseaseName, setDiseaseName] = useState('');
  const handleDiseaseName = (e: any) => {
    const disease = e.target.value;
    if (
      !diseaseKorRegexp.test(disease) &&
      !diseaseEngRegexp.test(disease)
    ) {
      setDiseaseErr(true);
      setDiseaseErrMsg('병명은 공백포함 한글 18자 혹은 영문 36자까지 입력가능');
    } else {
      setDiseaseErr(false);
      setDiseaseErrMsg('');
      setDiseaseName(disease);
    }
  };

  // 목표금액 설정
  const [goalFund, setGoalFund] = useState(0);
  const handleGoalFund = (e: any) => {
    const goal = e.target.value.slice(0, 10);
    if (goal < 1 || goal > 1000000000) {
      setGoalCheck(false);
      setGoalCheckMsg(''); 
      setGoalErr(true);
      setGoalErrMsg('모금액은 숫자로 10억까지 입력가능');
    } else if (!goalRegexp.test(goal)) {
      setGoalCheck(false);
      setGoalCheckMsg('');
      setGoalErr(true);
      setGoalErrMsg('모금액은 숫자로 10억까지 입력가능');
    } else {
      setGoalErr(false);
      setGoalErrMsg('');
      // 금액띄워주기
      setGoalCheck(true);
      setGoalCheckMsg(`${goal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')} 원`);
      setGoalFund(Number(goal));
    }
  };

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
          Authorization: accessToken
        }
      })
      .then((res) => {
        console.log('변환성공');
        console.log(res.data.fileUrl);
        setImgUrl(res.data.fileUrl);
        // return res.data.fileUrl;
      })
      .catch((err) => {
        console.log(err);
      })
  }


  // POST요청 보내기
  // const [imgUpload, setImgUpload] = useState(false);
  const sendPOST = async () => {
    
    // 값들 쭉 체크하면서 없는 값 있으면 함수종료
    // 동기처리 실패해서 페이지 첫 렌더링때도 senPost함수가 실행됨
    if (!goalFund || !birthDate || !diseaseName || !benName || !imgUrl) {
      return
    }

    const ben = {
      // hospitalId: hospitalId,
      beneficiaryAmountGoal: goalFund,
      beneficiaryBirthday: birthDate.getTime(),
      beneficiaryDisease: diseaseName,
      beneficiaryName: benName,
      beneficiaryPhoto: imgUrl,
    };
    console.log(ben);

    await axios
      .post('/beneficiary/beneficiaries/register', ben, {
        headers: {
          Authorization: accessToken
        }
      })
      .then((res) => {
        console.log(res);
        setIsLoading(false);
        navigate('/mypage');
        alert('수혜자가 성공적으로 등록되었습니다!');
      })
      .catch((err) => {
        console.log(err);
      })
  };

  useEffect(() => {
    sendPOST();
  }, [imgUrl]);

  // 로딩화면 띄우기
  const [isLoading, setIsLoading] = useState(false);

  // 유효성 검사
  const checkValidity = async () => {
    if (
      !image || !benName || !birthDate || !diseaseName || !goalFund
    ) {
      setNoValErr(true);
      alert('입력되지 않은 값이 있습니다.');
      return
    } else {
      setNoValErr(false);
    }

    if (
      !imgErr && !noValErr && !nameErr && !diseaseErr && !goalErr
    ) {
      // 로딩중 표시
      setIsLoading(true);
      // console.log("token: ", accessToken);
      getImgUrI();
      // const imgForAx = await getImgUrI();

      // console.log(imgForAx);

      // const body = {
      //   hospitalId: hospitalId,
      //   beneficiaryAmountGoal: goalFund,
      //   beneficiaryBirthday: birthDate.getTime(),
      //   beneficiaryDisease: diseaseName,
      //   beneficiaryName: benName,
      //   beneficiaryPhoto: imgForAx,
      // }

      // console.log(body);

      // await axios
      //   .post('/beneficiary/beneficiaries/register', body, {
      //     headers: {
      //       Authorization: A_TOKEN
      //     }
      //   })
      //   .then((res) => {
      //     console.log(res)
      //   })
      //   .catch((err) => {
      //     console.log(err)
      //   })
    }
  };

  return (
    <>
      <div className={styles.mainWrapper}>
        <div className={styles.registerTitle}>수혜자 등록하기</div>
        <div className={styles.registerWrapper}>
          {/* 이미지 입력 */}
          <div className={styles.imgInput}>
            {/* 이미지 선택하면 해당 이미지 띄우고 없으면 기본 이미지 */}
            {imgPreUrl ? (
              <div className={styles.withImgCircle}>
                <img className={styles.benImg} src={imgPreUrl} alt=""/>
              </div>
            ) : (
              <div className={styles.imgCircle} />   
            )}
            <label htmlFor="imgUp">
              <div className={styles.plusIcon} />
            </label>
            <input 
              type="file"
              id="imgUp"
              className={styles.inputFile}
              accept="image/jpg, image/png, image/jpeg"
              onChange={handleImage}
            />
          </div>
          {imgErr && (
            <div className={styles.centerErrMsg}>{imgErrMsg}</div>
          )}
          {/* 이름 입력 */}
          <div className={styles.nameInputWrapper}>
            <div className={styles.inputTitle}>수혜자 이름</div>
            <div className={styles.inputDiv}>
              <input 
                className={styles.nameInput}
                type="text"
                maxLength={5}
                placeholder="이름을 입력해 주세요"
                onChange={handleBenName}
              />
              {/* 이름 에러 처리 */}
              {nameErr && (
                <div className={styles.errMsg}>{nameErrMsg}</div>
              )}
            </div>
          </div>
          {/* 생일 입력 */}
          <div className={styles.birthInputWrapper}>
            <div className={styles.inputTitle}>수혜자 탄신일</div>
            <div className={styles.inputDiv}>
              <div className={styles.datepicker}>
                {/* 달력 */}
                <DatePicker 
                  dateFormat="yyyy년 MM월 dd일"
                  selected={birthDate}
                  onChange={(d: Date) => setBirthDate(d)}
                  maxDate={today}
                  todayButton={"Today"}
                  locale="ko"
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
            </div>
          </div>
          {/* 병명 입력 */}
          <div className={styles.diseaseInputWrapper}>
            <div className={styles.inputTitle}>수혜자 병명</div>
            <div className={styles.inputDiv}>
              <input 
                className={styles.diseaseInput}
                type="text"
                placeholder="병명을 입력해 주세요"
                onChange={handleDiseaseName}
              />
              {/* 병명 에러 처리 */}
              {diseaseErr && (
                <div className={styles.errMsg}>{diseaseErrMsg}</div>
              )}
            </div>
          </div>
          {/* 금액 입력 */}
          <div className={styles.fundInput}>
            <div className={styles.inputTitle}>목표금액</div>
            <div className={styles.inputDiv}>
              <input 
                className={styles.nameInput}
                type="text"
                maxLength={10}
                placeholder="목표금액을 입력해 주세요"
                onChange={handleGoalFund}
              />
              {goalErr && (
                <div className={styles.errMsg}>{goalErrMsg}</div>
              )}
              {goalCheck && (
                <div className={styles.goalMsg}>{goalCheckMsg}</div>
              )}
            </div>
          </div>
        </div>
        {isLoading ? (
          <div 
            className={styles.loadingBtn}
          >
            <img className={styles.load_img} src={Loading} alt="로딩중" />
          </div>
        ) : ( 
          <div 
            className={styles.registerBtn}
            onClick={checkValidity}  
          >
            등록하기
          </div>
        )}
      </div>
    </>
  )
}

export default BenRegister;