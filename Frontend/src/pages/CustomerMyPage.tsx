import React, { useEffect, useState } from 'react';
import styles from "./CustomerMyPage.module.css"
import image1 from './../assets/logo/heart1.png';
import image2 from './../assets/logo/star1.png';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAddressCard, faStar, faHospitalUser, faRightFromBracket, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store';
import { authActions } from '../store/auth';

const CustomerMyPage: React.FC = () => {
    const authInfo = useSelector((state:RootState)=> state.auth);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [log, setLog] = useState<{price:number, count:number}>({price:0 ,count:0});




    const logoutHandler = () => {
        dispatch(authActions.logout());
        console.log("로그아웃")
        localStorage.removeItem("access_token");
        localStorage.removeItem("refresh_token");
        axios.post("member/auth/logout").then(res => {
            axios.defaults.headers.common = {};
            navigate("/");
        }).catch((err) => {
            console.log(err);
            navigate("/");
        })
    }

    useEffect(() => {
        console.log("발동!")
        axios.get("/member/members/info").then((res) => {
            const name = res.data.name;
            dispatch(authActions.update({name}))
            setLog({price: res.data.totalAmount,count: res.data.totalFundCnt})
        })
    },[])

    return (
        <div className={styles.CustomerMyPage}>

            <div className={styles.user_profile}>
                <div className={styles.user_img_main}><img src={authInfo.profileImg} alt='profile' /></div>
                <div className={styles.user_name}>
                    <div className={styles.comment_greet}>반갑습니다. {authInfo.nickname}님!</div>
                    <div className={styles.comment_mypage}>{authInfo.nickname} 님의 마이페이지</div>
                </div>
            </div>

            <nav className={styles.user_menu}>
                <ul className={styles.user_list}>
                    <li onClick={()=> navigate("edit")}>
                        <div className={styles.menu_left}>
                            <FontAwesomeIcon className={styles.menu_logo} icon={faAddressCard} />
                            <div>개인정보 수정</div>
                        </div>
                        <FontAwesomeIcon icon={faChevronRight} />
                    </li>

                    {
                        authInfo.role==="ADMIN" ?
                        <>
                        <li><div className={styles.menu_left} onClick={() => navigate("/notice/create")}><FontAwesomeIcon className={styles.menu_logo} icon={faStar} /><div>공지사항 작성</div></div>  <FontAwesomeIcon icon={faChevronRight} /></li>
                        <li><div className={styles.menu_left} onClick={logoutHandler}><FontAwesomeIcon className={styles.menu_logo} icon={faRightFromBracket} onClick={logoutHandler} /><div>로그아웃</div></div> </li>
                        </>:<> <li><div className={styles.menu_left} onClick={() => navigate("favorite")}><FontAwesomeIcon className={styles.menu_logo} icon={faStar} /><div>관심 모금</div></div>  <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li><div className={styles.menu_left} onClick={() => navigate("donatelist")}><FontAwesomeIcon className={styles.menu_logo} icon={faHospitalUser} /><div>모금목록 조회</div></div> <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li><div className={styles.menu_left} onClick={() => navigate("restorelist")}><FontAwesomeIcon className={styles.menu_logo} icon={faHospitalUser} /><div>회복일지 조회</div></div> <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li><div className={styles.menu_left} onClick={logoutHandler}><FontAwesomeIcon className={styles.menu_logo} icon={faRightFromBracket} onClick={logoutHandler} /><div>로그아웃</div></div> </li></>
                    }
                   
                </ul>

            </nav>

            <div className={styles.user_log}>

                <div className={styles.user_donate}>
                    <div className={styles.user_img}><img src={image1} alt='heart' /></div>
                    <div className={styles.donate_title} >누적 기부액</div>
                    <div className={styles.donate_amount}>{log.price}원</div>
                </div>


                <div className={styles.user_donate}>
                    <div className={styles.user_img}><img src={image2} alt='star' /></div>
                    <div className={styles.donate_title}>기부 횟수</div>
                    <div className={styles.donate_amount}>{log.count}회</div>
                </div>
            </div>
        </div>
    );
};

export default CustomerMyPage;