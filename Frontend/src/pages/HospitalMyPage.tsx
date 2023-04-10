import React, { useEffect, useState } from 'react';
import styles from "./HospitalMyPage.module.css"
import image1 from './../assets/logo/heart1.png';
import image2 from './../assets/logo/star1.png';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faAddressCard, faMagnifyingGlassPlus,faStar, faHospitalUser, faHandHoldingDollar,faRightFromBracket, faChevronRight } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store';
import { authActions } from '../store/auth';

interface userInfo {
    name: string;
    totalAmount: number;
    totalFundCnt: number;
    role: String;
}
const HospitalMyPage: React.FC = () => {
    const authInfo = useSelector((state: RootState) => state.auth);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const accessToken = localStorage.getItem("access_token");

    useEffect(() => {
        // console.log(accessToken);
        axios.get("/member/members/info", {

        }).then((res) => {
            const name = res.data.name;
            dispatch(authActions.update({name}))
        })
    },[])


    const logoutHandler = () => {
        dispatch(authActions.logout());
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

    return (
        <div className={styles.HospitalMyPage}>

            <div className={styles.user_profile}>
                <div className={styles.user_img_main}><img src={authInfo.profileImg} alt='profile' /></div>
                <div className={styles.user_name}>
                    <div className={styles.comment_greet}>반갑습니다.  {authInfo.nickname}님!</div>
                    <div className={styles.comment_mypage}> {authInfo.nickname} 업체페이지</div>
                </div>
            </div>

            <nav className={styles.user_menu}>
                <ul className={styles.user_list}>
                    {/* <li onClick={() => navigate("edit")}>
                        <div className={styles.menu_left}>
                            <FontAwesomeIcon className={styles.menu_logo} icon={faAddressCard} />
                            <div>병원정보 수정</div>
                        </div>
                        <FontAwesomeIcon icon={faChevronRight} />
                    </li> */}
                    <li><div className={styles.menu_left} onClick={() => navigate("hosbenlist")}><FontAwesomeIcon className={styles.menu_logo} icon={faStar} /><div >수혜자 목록 조회하기</div></div>  <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li><div className={styles.menu_left} onClick={() => navigate("benregi")}><FontAwesomeIcon className={styles.menu_logo} icon={faHospitalUser} /><div >  수혜자 등록하기</div></div> <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li><div className={styles.menu_left} onClick={() => navigate("hosfundlist")}><FontAwesomeIcon  className={styles.menu_logo}  icon={faMagnifyingGlassPlus} /><div >진행중인 모금 목록 조회하기</div></div> <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li><div className={styles.menu_left} onClick={() => navigate("fundregi")}><FontAwesomeIcon className={styles.menu_logo} icon={faHandHoldingDollar}/><div >모금 시작하기</div></div> <FontAwesomeIcon icon={faChevronRight} /></li>
                    <li style={{ border: "none" }}><div className={styles.menu_left} onClick={logoutHandler}><FontAwesomeIcon className={styles.menu_logo} icon={faRightFromBracket} onClick={logoutHandler} /><div className={styles.sel}>로그아웃</div></div> </li>
                </ul>

            </nav>

            {/* <div className={styles.user_log}>

                <div className={styles.user_donate}>
                    <div className={styles.user_img}><img src={image1} alt='heart' /></div>
                    <div className={styles.donate_title} >누적 기부액</div>
                    <div className={styles.donate_amount}>2,000,000원</div>
                </div>


                <div className={styles.user_donate}>
                    <div className={styles.user_img}><img src={image2} alt='star' /></div>
                    <div className={styles.donate_title}>기부 횟수</div>
                    <div className={styles.donate_amount}>45회</div>
                </div>
            </div> */}
        </div>
    );
};

export default HospitalMyPage;