import styles from "./HosBeneficiaryCard.module.css";
import { HosBenInfo } from "../../models/hospitalmodels";
import { useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
// import { useNavigate } from "react-router";

interface Props {
  data: HosBenInfo;
}

const HosBeneficiaryCard: React.FC<Props> = ({ data }) => {
  const navigate = useNavigate();
  const percent: number =
    (data.beneficiaryAmountRaised / data.beneficiaryAmountGoal) * 100;
  const [beneficiaryStatus, setBeneficiaryStatus] = useState<string>(
    data.beneficiaryStatus
  );
  const loaded = useRef<Boolean>(false);

  // const navigate = useNavigate();

  useEffect(() => {
    if (loaded.current === false) {
      loaded.current = true;
    } else {
      axios
        .post(
          `/beneficiaries/edit/${data.beneficiaryId}`,
          { beneficiaryStatus: beneficiaryStatus },
          { params: { hospitalId: data.hospitalId } }
        )
        .catch((e) => {
          alert("환자 현황 변경 실패");
        });
    }
  }, [beneficiaryStatus, data]);

  return (
    <div className={`${styles.card_box} ${styles.grid_col_8}`}>
      <li className={styles.bene_img_box}>
        <div
          className={styles.bene_img}
          style={{backgroundImage: `url(${ data.beneficiaryPhoto})`, backgroundSize:"cover"}}
         
         
        ></div>
      </li>
      <li>{data.beneficiaryName}</li>
      <li>
        {new Date(data.beneficiaryBirthday).toLocaleDateString().slice(0, -1)}{" "}
      </li>
      <li>{data.beneficiaryDisease}</li>
      <li className={percent < 100 ? undefined : styles.font_red}>
        <div className={styles.progress_box}>
          <div className={`${styles.money_percent} ${styles.space_between}`}>
            <div className={styles.dona_curmoney}>
              {data.beneficiaryAmountRaised
                .toString()
                .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
              원
            </div>
            <div className={styles.dona_curpercen}>
              {percent < 100 ? percent + "%" : "완료"}
            </div>
          </div>
          <progress value={percent} max={100} />
        </div>
      </li>
      <li>
        <select
          name="status"
          value={beneficiaryStatus}
          onChange={(e) => {
            setBeneficiaryStatus(e.target.value);
          }}
          className={styles.select_box}
        >
          <option value="RAISING">모금 중</option>
          <option value="DONE">모금 완료</option>
          <option value="RECOVERING">회복 중</option>
        </select>
      </li>
      <li className={styles.btn_box}
        onClick={() => navigate('/diary')}
      >일지 작성</li>
      <li className={styles.btn_box}
        onClick={() => navigate('/benedit', { state: data })}>정보 수정</li>
    </div>
  );
};

export default HosBeneficiaryCard;
