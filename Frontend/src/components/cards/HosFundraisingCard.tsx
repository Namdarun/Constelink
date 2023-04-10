import styles from "./HosFundraisingCard.module.css";
import { HosFundraisingData } from "../../models/hospitalmodels";
import { floor } from "lodash";

interface Props {
    data: HosFundraisingData; 
    time: number;
};


const HosFundraisingCard: React.FC<Props> = ({ data, time }) => {
    const dayLeft:number = floor((data.fundraisingEndTime - time)/86400000);
    const hourLeft:number = floor(((data.fundraisingEndTime - time)%86400000)/3600000);
    const curValue = (data.fundraisingAmountRaised/data.fundraisingAmountGoal)*100;

    console.log(data.fundraisingThumbnail)
    return (
            <div className={`${styles.card_box} ${styles.grid_col_4}`} >
                <li><img className={styles.fund_img} src={data.fundraisingThumbnail?data.fundraisingThumbnail:"./circleuser.png"} onError={(e)=>{e.currentTarget.src="./circleuser.png"}} alt="fundraisingPhoto.jpg" ></img></li>
                <li>{data.beneficiaryName}</li>
                <li className={dayLeft<1?styles.time_alert:undefined}>{dayLeft}일 {hourLeft}시간</li>

                <li className={styles.progress_box}>
                <div className={styles.dona_goal}>{data.fundraisingAmountGoal.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원</div>
                     <progress className={styles.progress} value={curValue} max={100} />
                    <div className={styles.space_between} >
                        <div className={styles.dona_curmoney}>{data.fundraisingAmountRaised.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}원</div>
                        <div className={styles.dona_curpercen}>{curValue}%</div>
                    </div>
                </li>
            </div>
    );
};

export default HosFundraisingCard;