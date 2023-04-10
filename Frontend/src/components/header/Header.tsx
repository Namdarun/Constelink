import { useEffect, useState } from "react";
import styles from "./Header.module.css";
import { useSelector, useDispatch } from 'react-redux';
import { RootState } from "../../store";
import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faBars } from "@fortawesome/free-solid-svg-icons";
import  axios  from 'axios';
import { authActions } from './../../store/auth';

const Header: React.FC = () => {
    const [selectedMenu, setSelectedMenu] = useState(localStorage.getItem('selectedMenu') || 'default');

    const authInfo = useSelector((state: RootState) => state.auth);
    const navigate = useNavigate();
    const dispatch = useDispatch()
    useEffect(() => {
        const path = window.location.pathname;
        switch (path) {
            case '/':
                setSelectedMenu('/');
                break;
            case '/notice':
                setSelectedMenu('공지사항');
                break;
            case '/fundmain':
                setSelectedMenu('치료모금');
                break;
            case '/hospage':
                setSelectedMenu('치료일지');
                break;
            case '/finish':
                setSelectedMenu('치료달성');
                break;
            case '/mypage':
                setSelectedMenu('마이페이지');
                 break;
            default:
                setSelectedMenu('default');
        }
    }, []);

    useEffect(() => {
        localStorage.setItem('selectedMenu', selectedMenu);
    }, [selectedMenu]);

    const handleClick = (menu: string) => {
        setSelectedMenu(menu);
    };

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
    return (

        <div className={styles.Header}>
            <div className={styles.header_logo} onClick={() => {
                navigate("/");
                handleClick('/');}}>Constelink</div>


            <ul className={styles.header_menu} >
                <li style={{
                    color: selectedMenu === '공지사항' ? '#6360ab' : 'black',
                    borderBottom: selectedMenu === '공지사항' ? '3px solid #6360ab' : 'none'
                }}
                    onClick={() => {
                        handleClick('공지사항');
                        navigate('/notice')
                    }} >공지사항</li>
                <li style={{
                    color: selectedMenu === '치료모금' ? '#6360ab' : 'black',
                    borderBottom: selectedMenu === '치료모금' ? '3px solid #6360ab' : 'none'
                }}
                    onClick={() => {
                        handleClick('치료모금');
                        navigate('/fundmain')
                    }}>치료모금</li>
                <li style={{
                    color: selectedMenu === '치료일지' ? '#6360ab' : 'black',
                    borderBottom: selectedMenu === '치료일지' ? '3px solid #6360ab' : 'none'
                }}
                    onClick={() => {
                        handleClick('치료일지');
                        navigate('/diary')
                    }}>치료일지</li>
                <li style={{
                    color: selectedMenu === '치료달성' ? '#6360ab' : 'black',
                    borderBottom: selectedMenu === '치료달성' ? '3px solid #6360ab' : 'none'
                }}
                    onClick={() => {
                        handleClick('치료달성');
                        navigate('/finish')
                    }}>치료달성</li>
            </ul>
            {
                authInfo.isAuthenticated ? <div className={styles.header_login} onClick={() => {
                    navigate("/mypage");
                    setSelectedMenu("마이페이지");

                }}><img onError={(e)=> {e.currentTarget.src="/circleuser.png"} } className={styles.header_profile} src={authInfo.profileImg} alt="profile" /></div> : <div className={styles.header_login} onClick={() => navigate('/login')}>로그인</div>
            }
            {/* 반응형 사이드바 */}
            <div className={styles.interactive}>
                <nav className={styles.nav}>
                    <ul className={styles.ul}>
                        <li className={styles.dropdown}>
                            <div className={styles["dropdown-menu"]}><FontAwesomeIcon style={{ fontSize: "24px" }} icon={faBars} /></div>
                            <div className={styles["dropdown-content"]}>
                                <span className={styles.a} onClick={()=>navigate("/notice")}>공지사항</span>
                                <span className={styles.a} onClick={()=>navigate("/fundmain")}>치료모금</span>
                                <span className={styles.a} onClick={()=>navigate("/diary")}>치료일지</span>
                                <span className={styles.a} onClick={()=>navigate("/finish")}>치료달성</span>
                                {
                                    authInfo.isAuthenticated ? <span> <span className={styles.a} onClick={()=>{
                                        authInfo.role === 'USER' ?  navigate("/mypage") : authInfo.role === 'ADMIN'? navigate("/mypage"): navigate("/mypage");
                                       
                                    }}>마이페이지</span> <span className={styles.a} onClick={logoutHandler}>로그아웃</span></span> : <span className={styles.a} onClick={()=>navigate("/login")}>로그인</span>
                                }

                            </div>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>


    )
}

export default Header;
