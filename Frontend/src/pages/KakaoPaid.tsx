import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import styles from "./FundPayment.module.css";
// import dotenv from 'dotenv';


import Web3 from "web3";
import { AbiItem } from 'web3-utils';
import { FUND_ABI } from "../web3js/FUND_ABI";
import { TransactionConfig } from 'web3-core';
import { TransactionReceipt } from 'web3-core/types';

import Mining from "../assets/img/mining.gif";

interface recievedata {
  beneficiaryBirthday: number;
  beneficiaryDisease: string;
  beneficiaryId: number
  beneficiaryName: string
  beneficiaryPhoto: string
  beneficiaryStatus: string
  categoryName: string
  fundraisingAmountGoal: number
  fundraisingAmountRaised: number
  fundraisingBookmarked: boolean
  fundraisingEndTime: number
  fundraisingId: number
  fundraisingIsDone: boolean
  fundraisingPeople: number
  fundraisingStartTime: number
  fundraisingStory: string
  fundraisingThumbnail: string
  fundraisingTitle: string
  hospitalName: string
}


const AUTH_TOKEN = window.localStorage.getItem('access_token');
const MM_KEY = process.env.REACT_APP_MM_PRIVATE_KEY;
const FUND_CA = "0x07A8A469ca0D02049599874580a0aBA76dd34F18";


// const TEST_PUB_FUND_CA = "0x962aDFA41aeEb2Dc42E04586dBa143f2404FD10D";


// 이 페이지에서 메타마스크와 연결하고 토큰mint, donate 해야할듯?
const KakaoPaid: React.FC = () => {




  // console.log(MM_KEY);

  const navigate = useNavigate();

  // 현재 접속한 유저의 metamask address 가져오기
  const [web3, setWeb3] = useState<Web3 | null>(null);
  const [address, setAddress] = useState<string | null>(null);
  const [contract, setContract] = useState<any | null>(null);

  // 카카오 결제완료 후 토큰 받아오기, 금액 설정
  const [money, setMoney] = useState(0);
  // 기부 상세정보 받아오기
  const [info, setInfo] = useState<recievedata>();
  const [id, setId] = useState(0);

  // 카카오 결제 토큰 쿼리에서 받아서 쓰기
  const pgToken = window.location.search.substring(10);


  // 0. id 설정
  useEffect(() => {
    setId(Number(window.localStorage.details.substr(17, 1)));
  }, [])


  // 1. 카카오에 token으로 결제 정보 받아오는 요청 보내기
  // 요청 성공하면 true로바꾸고 다음단계로
  const [kakaoSuccess, setKakaoSuccess] = useState(false);
  useEffect(() => {


    axios.get(`/member/payments/success?pg_token=${pgToken}`, {
      headers: {
        Authorization: AUTH_TOKEN
      }
    })
      .then((res) => {
        // console.log(res);
        // console.log(localStorage.getItem('details'));
        console.log('1. 카카오 성공');
        localStorage.setItem('money', res.data.amount.total);
        setMoney(res.data.amount.total);
        // 타입스크립트 땜시 null일 때 예외 처리해주어야 함
        setInfo(JSON.parse(localStorage.getItem('details') || '{}'));
        setKakaoSuccess(true);
      })
      .catch((err) => {
        console.log(err);
      })

  }, [pgToken])


  // 2. 메타마스크 연결하는 단계 (필수조건: 카카오결제가 성공적으로 이루어 졌다면)
  // 금액 받아오면 web3통신 시작!
  // 계정 주소 불러오고, 펀딩 컨트랙트 연결 성공하면 true로 바꿔주기
  const [web3Success, setWeb3Seccess] = useState(false);
  useEffect(() => {
    const detectWeb3 = async () => {

      if (typeof window.ethereum !== "undefined") {
        // MetaMask is installed & create an web3 instance
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

    // 결제가 성공적으로 이루어졌다면 메타마스크 연결
    if (kakaoSuccess) {
      detectWeb3();
      console.log('2. 메타마스크 연결 성공');
      setWeb3Seccess(true);
    }
  }, [kakaoSuccess]);


  // mint컨트랙트 보내는 중 (로딩화면 띄우기 위해)
  const [isMinting, setIsMinting] = useState(false);
  // transactionhash 저장 (back에 전송해 주어야 함)
  const [tranHash, setTranHash] = useState('');

  // 3. 토큰 민팅 트랜젝션
  async function sendTransactionMint() {
    setIsMinting(true);
    console.log('3. 토큰 민팅 시작');

    if (web3) {
      const master = web3.eth.accounts.privateKeyToAccount(MM_KEY!);
      // console.log(master);
      const txParams: TransactionConfig = {

        from: master.address,
        to: FUND_CA,
        gas: 1000000,
        data: contract.methods.mint(address, money).encodeABI(),
        nonce: await web3.eth.getTransactionCount(master.address),
        chainId: 11155111,
      };

      const signedTX = await master.signTransaction(txParams);
      // console.log('이게 signedTX');
      // console.log(signedTX.rawTransaction);
      // console.log('입니다');

      const receipt: TransactionReceipt = await web3.eth.sendSignedTransaction(signedTX.rawTransaction!);
      console.log(`Mint Transaction hash: ${receipt.transactionHash}`);
      // setTranHash(receipt.transactionHash);
      // setIsDone(true);
      sendTransactionDonate();
      // setTimeout(sendTransactionDonate, 5000);
    } else {
      console.log('Web3 is not available');
    };
  }

  // 4. 기부 트랜젝션
  // 트랜젝션 끝나면 기부내역 저장하는 함수 실행
  const [txDone, setTxDone] = useState(false);
  async function sendTransactionDonate() {
    console.log('4. 토큰 기부 시작');
    // console.log(id);
    // console.log(typeof id);
    console.log(info?.fundraisingId);
    console.log(typeof info?.fundraisingId);

    const txHash = await contract.methods
      .fundRaising(FUND_CA, money, Number(info?.fundraisingId))
      .send({ from: address });
    console.log("Donate Transaction hash:", txHash);
    setTranHash(String(txHash));
    setTxDone(true);
  }


  // 3, 4 메타 마스크 연결되어 있으면 트랜젝션 보내기
  // 3. 토큰 민팅 => 4. 토큰 기부
  // 토큰 기부는 민팅함수 안에 있어 토큰민팅이 성공적으로 이루어지면 순차적으로 진행됨
  const handleDonate = () => {
    if (web3Success) {
      sendTransactionMint();
    }
  }

  // 5. 토큰 기부 완료되면
  // const [isDone, setIsDone] = useState(false);
  useEffect(() => {
    // 토큰 기부하면 DB에 기부 저장하기
    const saveDonation = async () => {

      // console.log('400해결');
      // console.log(info);
      // console.log(typeof info);
      // console.log(tranHash);
      // console.log(typeof tranHash)

      const body = {
        fundraisingId: info?.fundraisingId,
        donationPrice: money,
        donationTransactionHash: tranHash,
        hospitalName: info?.hospitalName,
        beneficiary_id: info?.beneficiaryId,
        beneficiaryName: info?.beneficiaryName,
        beneficiaryDisease: info?.beneficiaryDisease,
        fundraisingTitle: info?.fundraisingTitle,
        fundraisingThumbnail: info?.fundraisingThumbnail
      };

      // console.log('나이스바디');
      // console.log(body);

      await axios.post('/member/donations/save', body, {
        headers: {
          Authorization: AUTH_TOKEN
        }
      })
        .then((res) => {
          console.log('5. 도네 저장 성공');
          console.log(res);
        })
        .catch((err) => {
          console.log('5. 도네 저장 실패');
          console.log(err);
        })
    }

    // 6. 기부, 저장완료되면 홈으로 이동하고
    // 로컬스토리지 비워주기
    if (txDone) {
      alert('토큰 기부가 완료되었습니다!');
      // 도네이션 정보 저장
      saveDonation();

      navigate('/');

      localStorage.clear();
    }
  }, [txDone])


  return (
    <div className={styles.mainWrapper}>
      {/* 수금 페이지 메인 배너 */}
      {isMinting ? (
        <div className={styles.mainBanner}>
          <div className={styles.bannerTitle}>토큰을 기부하는 중입니다 ...</div>
          <div className={styles.bannerSubTitle}>잠시만 기다려 주세요!</div>
        </div>
      ) : (
        <div className={styles.mainBanner}>
          <div className={styles.bannerTitle}>여기까지 오신 당신, 당신은 멋집니다.</div>
          <div className={styles.bannerSubTitle}>모금된 자금은 공정하고 투명하게 쓰이게됩니다.</div>
        </div>
      )}


      {/* 메인 페이지 */}
      <div className={styles.mainArticle}>

        {/* 치료비 후원과정 */}
        <div className={styles.articleStep}>
          <div className={styles.articleStepTitle}>치료비 후원 신청</div>
          <div className={styles.articleStepSubTitle}>치료비는 다음과 같은 과정으로 후원됩니다.</div>
          <div className={styles.articleSteps}>
            <div className={styles.articleStepOne}>
              <div className={styles.voidCircle}></div>
              <div className={styles.stepDetail}>
                <div className={styles.stepOneNumber}>STEP1.</div>
                <div className={styles.stepOneTodo}>수혜자 정보확인</div>
              </div>
            </div>
            <div className={styles.arrowDiv} />
            <div className={styles.articleStepTwo}>
              <div className={styles.voidCircle}></div>
              <div className={styles.stepDetail}>
                <div className={styles.stepNumber}>STEP2.</div>
                <div className={styles.stepTodo}>카카오페이 결제</div>
              </div>
            </div>
            <div className={styles.arrowDiv} />
            <div className={styles.articleStepThree}>
              <div className={styles.voidStar}></div>
              <div className={styles.stepDetail}>
                <div className={styles.stepNumber}>STEP3.</div>
                <div className={styles.stepTodo}>토큰으로 기부하기</div>
              </div>
            </div>
          </div>
        </div>

        {/* 토큰 제작 중 */}
        {isMinting ? (
          <div className={styles.article_info}>
            <div className={styles.gif_div}>
              <img className={styles.gif} src={Mining} alt="캐는 중..." />
            </div>
          </div>
        ) : (
          <>
            <div className={styles.moneyCheck}>
              <div className={styles.moneyCheckKey}>총 후원토큰 |</div>
              <div className={styles.moneyCheckValue}>{localStorage.getItem('money')?.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</div>
              <div className={styles.moneyCheckCurrency}>CSTL</div>
            </div>
            <div
              className={styles.fundingBtn}
              onClick={handleDonate}
            >
              기부하기
            </div>
          </>
        )}
      </div>
    </div>
  )
}

export default KakaoPaid;