import React from "react";
import styles from "./DonationCard.module.css";
import { DonationData } from "../../models/donatecard";
import { useState, useEffect } from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faStar } from "@fortawesome/free-solid-svg-icons";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import axios from "axios";
import { useSelector } from "react-redux";
import { RootState } from "../../store";
import { useNavigate } from "react-router-dom";

interface Props {
  data: DonationData;
}

const DonationCard: React.FC<Props> = ({ data }) => {
    const authInfo = useSelector((state: RootState) => state.auth);
    const [curValue, setCurValue] = useState(0);
    const [curMoney, setCurMoney] = useState(0);
    const navigate = useNavigate();
    let percentage =
      (data.fundraisingAmountRaised / data.fundraisingAmountGoal) * 100;
    let demicalDay = Math.floor(
      (data.fundraisingEndTime - new Date().getTime()) / (3600 * 24 * 1000)
    );
    const goalMoney = data.fundraisingAmountRaised;
  
    // 북마크 설정
    const [isMark, setIsMark] = useState(data.fundraisingBookmarked);
  
    useEffect(() => {
      const intervalIdPercent = setInterval(() => {
        if (curValue < percentage) setCurValue((curValue) => curValue + 1);
      }, 10);
  
      return () => clearInterval(intervalIdPercent);
    }, [curValue, percentage]);
  
    useEffect(() => {
      const intervalIdMoney = setInterval(() => {
        if (curMoney < goalMoney) {
          setCurMoney((curMoney) => curMoney + 14124);
        } else {
          setCurMoney(goalMoney);
        }
      }, 0.05);
  
      return () => clearInterval(intervalIdMoney);
    }, [curMoney, goalMoney]);
  
    const bookHandler = () => {
      const accessToken = localStorage.getItem("access_token");
      axios.defaults.headers.common["authorization"] = accessToken;
      axios
        .post("/fundraising/bookmarks", {
          memberId: 1,
          fundraisingId: data.fundraisingId,
        })
        .then((res) => {
          axios.defaults.headers.common = {};
          setIsMark(res.data);
          console.log(res);
        })
        .catch((err) => {
          axios.defaults.headers.common = {};
        });
    };
  
    useEffect(() => {}, [setIsMark]);
  
    return (
      <div
        className={styles.DonationCard}
        style={{
          backgroundImage: `linear-gradient(to top, rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0)) ,url(${data.fundraisingThumbnail}), url("./errorImg.jpg")`,
          backgroundSize: "cover",
          backgroundRepeat: "no-repeat",
        }}
      >
        <div className={styles.bookmark}>
          {authInfo.role === "MEMBER" ? (
            <FontAwesomeIcon
              onClick={bookHandler}
              icon={faStar as IconProp}
              color={isMark ? "yellow" : "grey"}
            />
          ) : (
            ""
          )}{" "}
        </div>
  
        <div
          className={styles.dona_box}
          onClick={() => navigate(`/fundmain/funddetail/${data.fundraisingId}`)}
        >
          <div className={styles.dona_type}>{data.categoryName}</div>
          <div className={styles.dona_title}>{data.fundraisingTitle}</div>
          <div className={styles.dona_hospital}>{data.hospitalName}</div>
          <div className={styles.dona_deadline}>D-{demicalDay + 1}</div>
          <div className={styles.progress_box}>
            <progress value={curValue} max={100} />
  
            <div className={styles.money_percent}>
              <div className={styles.dona_curmoney}>
                {curMoney.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",")}원
              </div>
              <div className={styles.dona_curpercen}>{curValue}%</div>
            </div>
        </div>
      </div>
    </div>
   
  );
};

export default DonationCard;
