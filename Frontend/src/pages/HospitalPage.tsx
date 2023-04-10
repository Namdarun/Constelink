import { useDispatch } from "react-redux";
import styles from "./HospitalPage.module.css";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { authActions } from "../store/auth";

const HospitalPage: React.FC = () => {

  const navigate = useNavigate();
  const hospitalName: string = '삼성서울병원';
  const dispatch = useDispatch();
 
  const logoutHandler = ()=>{
      const accessToken = localStorage.getItem('access_token');
      const refreshToken = localStorage.getItem('refresh_token');
      axios.defaults.headers.common['authorization'] = accessToken;
      axios.defaults.headers.common['refresh'] = refreshToken;
      axios.post("/auth/logout").then(res=>{
          console.log(res);
          localStorage.removeItem("access_token");
          localStorage.removeItem("refresh_token");
          dispatch(authActions.logout());
          navigate("/")
      
      }).catch((err)=>{
          console.log(err);
      })
  }
  return (
    <>
      <div className={styles.mainWrapper}>
        <div className={styles.hospitalWrapper}>
          <div className={styles.hospitalIcon}>
            <div className={styles.hospitalName}>{hospitalName.substring(0, 1)}</div>
          </div>
          <div className={styles.hospitalText}>
            {hospitalName} 님의 마이페이지
          </div>
        </div>
        <div className={styles.menuWrapper}>
          <div className={styles.menuBar}>
            <div className={styles.menuIcon}>
              <div className={styles.userIcon}/>
            </div>
            <div className={styles.menuText}>
              수혜자 목록 조회하기
            </div>
            <div className={styles.menuArrow} onClick={() => navigate('/hosbenlist')}></div>
          </div>
          <div className={styles.menuBar}>
            <div className={styles.menuIcon}>
              <div className={styles.userIcon}/>
              <div className={styles.plusIcon}/>
            </div>
            <div className={styles.menuText}>
              수혜자 등록하기
            </div>
            <div className={styles.menuArrow} onClick={() => navigate('/benregi')} />
          </div>
          <div className={styles.menuBar}>
            <div className={styles.menuIcon}>
              <div className={styles.fileIcon}/>
            </div>
            <div className={styles.menuText}>
              진행중인 모금 목록 조회하기
            </div>
            <div className={styles.menuArrow} onClick={() => navigate('/hosfundlist')}></div>
          </div>
          <div className={styles.menuBar}>
            <div className={styles.menuIcon}>
              <div className={styles.fileIcon}/>
              <div className={styles.plusIcon}/>
            </div>
            <div className={styles.menuText}>
              모금 시작하기
            </div>
            <div className={styles.menuArrow} onClick={() => navigate('/fundregi')}/>
          </div>
          <div className={styles.menuBar}>
            <div className={styles.menuIcon}>
              <div className={styles.userIcon}/>
            </div>
            <div className={styles.menuText} onClick={logoutHandler}>
              로그아웃
            </div>
          
          </div>
        </div>
      </div> 
    </>
  )
}

export default HospitalPage;