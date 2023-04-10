import axios from "axios";
import styles from "./FinishFundList.module.css";
import { useEffect, useMemo, useState } from "react";
import { DonationData } from "./../models/donatecard";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import crown from "../assets/logo/pngegg.png";

// .FinishFundList::before 여기에 전체 백그라운드 컬러
// .raising_star1, .raising_star2 각각 애니메이션 delay가 다른 별
// .star_box 별 담는 박스
// @keyframe raisingStar 별 애니메이션
// @keyframes card_star 카드 안의 별
// @keyframes show 카드안의 환자관련 문구 올라가는 애니메이션
// @keyframes hide 카드안의 Constelink 로고 사라지는 애니메이션

const FinishFundList = () => {
  const [finishList, setFinishList] = useState<DonationData[]>([]);

  const randomStars = useMemo(
    () => (
      <div key="stars-1" className={styles.star_box}>
        {Array(100)
          .fill(null)
          .map((item: null, index) => (
            <div
              key={`star-${index}`}
              className={`${styles[`raising_star${index % 2}`]}`}
              style={{
                top: `${Math.random() * 1400 + 1200}px`,
                right: `${Math.random() * 1460}px`,
              }}
            ></div>
          ))}
      </div>
    ),
    []
  );
  useEffect(() => {
    axios
      .get(
        "/fundraising/fundraisings/withbeneficiaryinfo?page=1&size=5&sortBy=FINISHED&memberId=0"
      )
      .then((res) => {
        setFinishList(res.data.content);
      });
  }, []);
  return (
    <div className={styles.FinishFundList}>
      {/* <div className={styles.finish_title}>
                Constelink로 연결된 인연
            </div> */}
      <div className={styles.relative_box}>
        <div className={styles.crown_section}>
          <img src={crown} alt="crown" />
        </div>
        <div className={styles.grid_section}>
          {finishList.map((it, idx) => {
            return (
              <div
                className={styles.grid_card}
                key={idx}
                style={{
                  backgroundImage: ` linear-gradient(to top, rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0)),url(${it.fundraisingThumbnail})`,
                  backgroundSize: "cover",
                  backgroundRepeat: "no-repeat",
                }}
              >
                <div className={styles.card_success}>
                  <div className={styles.success}>Constelink</div>
                </div>
                <div className={styles.card_star}>
                  <FontAwesomeIcon icon={faStar} />
                </div>
                <div className={styles.show_comment}>
                  <div style={{ fontSize: "24px" }}>
                    {it.beneficiaryName} 환자
                  </div>
                  <br />
                  <div
                    style={{
                      fontSize: "18px",
                      marginBottom: "5px",
                      border: "1px solid white",
                      width: "90px",
                      textAlign: "center",
                      borderRadius: "5px",
                    }}
                  >
                    모금기간
                  </div>

                  <div style={{ fontSize: "16px" }}>
                    {new Date(it.fundraisingEndTime).toLocaleDateString()}
                  </div>
                  <br />
                  <div
                    style={{
                      fontSize: "18px",
                      marginBottom: "5px",
                      border: "1px solid white",
                      width: "90px",
                      textAlign: "center",
                      borderRadius: "5px",
                    }}
                  >
                    총 모금액
                  </div>
                  <div style={{ fontSize: "16px" }}>
                    {it.fundraisingAmountRaised}원
                  </div>

                  <br />
                  <div
                    style={{
                      fontSize: "18px",
                      border: "1px solid white",
                      borderRadius: "5px",
                      width: "90px",
                      textAlign: "center",
                      marginBottom: "5px",
                    }}
                  >
                    치료 내역
                  </div>
                  {it.fundraisingWillUse.split("/").map((item, index) => (
                    <div
                      style={{ fontSize: "16px" }}
                      key={`${it.fundraisingId}-${index}`}
                    >
                      {item}
                    </div>
                  ))}

                  <br />
                  <div style={{ fontSize: "18px", marginBottom: "5px" }}>
                    후원자
                    <span style={{ fontSize: "24px", fontWeight: "bold" }}>
                      {" "}
                      {it.fundraisingPeople}명
                    </span>{" "}
                  </div>
                  <br />
                  <div style={{ fontSize: "20px", fontWeight: "bold" }}>
                    {it.beneficiaryDisease}
                    {(it.beneficiaryDisease.charCodeAt(
                      it.beneficiaryDisease.length - 1
                    ) -
                      44032) %
                      28 ===
                    0
                      ? "를"
                      : "을"}{" "}
                    {it.hospitalName}
                  </div>
                </div>
              </div>
            );
          })}
        </div>
        {randomStars}
      </div>
    </div>
  );
};

export default FinishFundList;
