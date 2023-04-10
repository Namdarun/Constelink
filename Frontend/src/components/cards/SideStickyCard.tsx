import styles from "./SideStickyCard.module.css";
import { A11y } from 'swiper';
interface Props {
  imageUrl: string;
}

const SideStickyCard: React.FC = () => {
  return (
    <div className={`${styles.sticky_box} `}>
      <div className={styles.ad_box}>
          
        <div className={styles.box_top}>
          <div className={styles.top_ment}>
            
          </div>
           </div>

        <div className={styles.box_bottom}>
          
        <div className={styles.bottom_ment}>
            Constelink
        </div>
          
          <div className={styles.dis_btn}>

            <div className={styles.dis_button}>이동 ▶</div>
          </div>
          </div>

      </div>
    </div>
  );
};

export default SideStickyCard;
