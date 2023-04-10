import { useEffect, useState } from "react";

import styles from "./FundDetail.module.css";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

interface recievedata {
  beneficiaryBirthday: number;
  beneficiaryDisease: string;
  beneficiaryId: number;
  beneficiaryName: string;
  beneficiaryPhoto: string;
  beneficiaryStatus: string;
  categoryName: string;
  fundraisingAmountGoal: number;
  fundraisingAmountRaised: number;
  fundraisingBookmarked: boolean;
  fundraisingEndTime: number;
  fundraisingId: number;
  fundraisingIsDone: boolean;
  fundraisingPeople: number;
  fundraisingStartTime: number;
  fundraisingStory: string;
  fundraisingThumbnail: string;
  fundraisingTitle: string;
  hospitalName: string;
  fundraisingWillUse: string;
}

const FundDetail: React.FC = () => {
  const [curValue, setCurValue] = useState(0);

  const navigate = useNavigate();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const [detailData, setDetailData] = useState<recievedata>();
  const { id } = useParams<{ id: string }>();

  // 모금 데이터 받아오기
  useEffect(() => {
    axios.get(`/fundraising/fundraisings/${id}?memberId=0`).then((res) => {
      console.log(res.data);
      setDetailData(res.data);
    });
  }, [id]);

  // 디데이, 퍼센트 계산
  const [dday, setDday] = useState(0);
  const [fundPer, setFundPer] = useState(0);

  // 사용처 list 선언
  const [uses, setUses] = useState<string[]>();
  useEffect(() => {
    if (detailData) {
      setDday(
        Math.floor(
          (detailData.fundraisingEndTime - new Date().getTime()) /
            (3600 * 24 * 1000)
        )
      );
      let per =
        (detailData.fundraisingAmountRaised /
          detailData.fundraisingAmountGoal) *
        100;
      setFundPer(Number(per.toFixed()));

      let useString = detailData.fundraisingWillUse;
      let uses = useString.split("/");
      setUses(uses);
    }
  }, [detailData]);

  // 바 채우기
  useEffect(() => {
    const intervalIdPercent = setInterval(() => {
      if (curValue < fundPer) setCurValue((curValue) => curValue + 1);
    }, 10);
    return () => clearInterval(intervalIdPercent);
  }, [curValue, fundPer]);

  return (
    <div>
      <div className={styles.mainWrapper_box}>
        <div className={styles.mainWrapper}>
          {/* 모금썸네일(배너) */}
          <div
            className={styles.fundMain}

            style={{
              backgroundImage: `linear-gradient(to top, rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0)) ,url(${detailData?.fundraisingThumbnail}), url("./errorImg.jpg")`,
              backgroundSize: "cover",
              backgroundRepeat: "no-repeat",
            }}
      
          >
            <div className={styles.fundAbstract}>
              <div className={styles.fundTitle}>
                {/* 카테고리 */}
                <div className={styles.fundCategory}>
                  {detailData?.categoryName}
                </div>
                {/* 병원이름 */}
                <div className={styles.fundHospital}>
                  {detailData?.hospitalName}
                </div>
                {/* 병명 */}
                <div className={styles.fundBrief}>
                  {detailData?.beneficiaryDisease}
                </div>
                {/* 디데이 */}
                <div className={styles.fundDday}>D-{dday}</div>
              </div>
              {/* 모금알리기 = 링크공유 */}
              <div className={styles.fundShare}>
                <div className={styles.shareTitle}>모금알리기</div>
                <div className={styles.shareSub}>
                  모두에게 나눔을 공유하세요
                </div>
                <div className={styles.shareBtn}>
                  <span className={styles.shareBtnText}>모금알리기</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* 배너 밑 본문 */}
      <div className={styles.storyWrapper_box}>
        <div className={styles.storyWrapper}>
          <div className={styles.storyDetail}>
            {/* 모금 제목 */}
            <div className={styles.storyHeader}>
              <div className={styles.storyHeaderText}>
                {/* {detailData?.fundraisingTitle} */}
                "Constelink와 함께하는 여러분 덕에 많은 이 들이 새 삶을 살고있습니다. 많은 이들의 별자리가 되어주세요. Constelink는 늘 여러분 곁에 있습니다. 모두 콘스텔링크 하세요. "
              </div>
            </div>
            <div className={styles.storyContent}>
              <div className={styles.storyContentLogo}>Constelink Story</div>
              <div className={styles.storyContentWrapper}>
                {/* 수혜자 사진 */}
                <div
                  className={styles.storyContentImg}
                  style={{
                    backgroundImage: `url(${detailData?.beneficiaryPhoto})`,
                  
                  }}
                ></div>
                {/* 모금 내용 */}
                <div
                  className={styles.storyContentText}
                  dangerouslySetInnerHTML={{
                    __html: detailData?.fundraisingStory || "",
                  }}
                ></div>
              </div>
            </div>
          </div>
          <div className={styles.fundingCard}>
            <div className={styles.fundingBeneficiary}>
              {/* 수혜자 한마디 */}
              {/* 추후에 수정해야 할지도 */}
              <div className={styles.benefitTitle}>
              {detailData?.fundraisingTitle}
              </div>
              {/* 수혜자 정보 */}
              <div className={styles.benefitMan}>
                <div className={styles.benefitTag}>수혜자:</div>
                {/* 수혜자 이름 */}
                <div className={styles.benefitName}>
                  {detailData?.beneficiaryName}
                </div>
                {/* 수혜자 사진 */}
                <div
                  className={styles.benefitImg}
                  style={{
                    backgroundImage: `url(${detailData?.beneficiaryPhoto})`,
                    backgroundSize: "cover",
                    backgroundPosition: "center",
                  }}
                />
              </div>
            </div>
            <div className={styles.fundingUsage}>
              <div className={styles.usageTag}>
                치료비는 다음과 같은 곳에 사용됩니다
              </div>
              {/* 사용처 표시 */}
              {uses?.map((use) => {
                return (
                  <li key={use} className={styles.usageList}>
                    • {use}
                  </li>
                );
              })}
                 <li className={styles.usageList}>
                    • 약제비
                  </li>

                  <li className={styles.usageList}>
                    • 주사비
                  </li>

                  <li className={styles.usageList}>
                    • 입원비
                  </li>

              {/* 병원이름 */}
              <div className={styles.usageHospital}>
                {detailData?.hospitalName}
              </div>
            </div>
            {/* 모금 하기 버튼 */}
            <div
              className={styles.fundingBtn}
              onClick={() =>
                navigate(`/fundpayment/kakao/${detailData?.fundraisingId}`)
              }
            >
              <div className={styles.fundingBtnText}>모금동참</div>
            </div>
            {/* 모금률 바 */}
            <div className={styles.fundingBar}>
              <div className={styles.fundingTag}>모금도달률</div>
              {/* 모금률 계산 */}
              <div className={styles.fundingPct}>{fundPer}%</div>
              <div className={styles.star} />
              <progress
                className={styles.fundDetailBar}
                value={curValue}
                max={100}
              />
              <div className={styles.fundingGoal}>
                {detailData?.fundraisingAmountGoal
                  .toString()
                  .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                원
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FundDetail;
