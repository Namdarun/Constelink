import styles from "./Login.module.css";
import kakao from "../assets/logo/login_kakao.png";
import google from "../assets/logo/login_google.png";
import { useEffect } from "react";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { authActions } from "./../store/auth";
import { Link, useNavigate } from "react-router-dom";
import { RootState } from "../store";
const Login: React.FC = () => {
  const authInfo = useSelector((state: RootState) => state.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();
  console.log();

  useEffect(() => {
    if (authInfo.isAuthenticated) {
      console.log("현재 로그인 상태로 로그인 페이지 접근 불가");
      navigate("/");
    }
  });

  useEffect(() => {
    const param = new URLSearchParams(window.location.search);
    const connect_id = param.get("connect-id");
    const flag = param.get("flag");
    console.log(connect_id, flag);
    if (connect_id !== null && flag !== null) {
      let params: any = { key: connect_id, flag: flag };
      console.log(params, "--------------");

      axios
        .post("member/auth/login", params, { withCredentials: true })
        .then((res) => {
          console.log(res);
          localStorage.setItem("access_token", res.headers.authorization);
          localStorage.setItem("refresh_token", res.headers.refresh);
          axios.defaults.headers.common["authorization"] =
            res.headers.authorization;
          const [name, profileImg, role, email]: string[] = [
            res.data.nickname,
            res.data.profile,
            res.data.role,
            res.data.email,
          ];
          dispatch(authActions.login({ name, profileImg, role, email }));
          navigate("/");
        })
        .catch((err) => {
          console.log(err);
        });
    }
  }, [dispatch, navigate]);

  return (
    <div className={styles.Login}>
      <header className={styles.login_header} onClick={() => navigate("/")}>
        <div className={styles.logo_title}>Constelink</div>
        <div className={styles.logo_sub}>블록체인 기반, 치료비 모금 플랫폼</div>
      </header>

      <ul className={styles.login_menu}>
        <li
          id={styles.google_list}
          onClick={() =>
            (window.location.href =
              "http://j8a206.p.ssafy.io/member/oauth2/authorization/google")
          }
        >
          <img className={styles.google_logo} src={google} alt="google-img" />{" "}
          <div>Google로 시작하기</div>
        </li>
        <li
          id={styles.kakao_list}
          onClick={() =>
            (window.location.href =
              "http://j8a206.p.ssafy.io/member/oauth2/authorization/kakao")
          }
        >
          <img className={styles.kakao_logo} src={kakao} alt="kakao-img" />
          <div>카카오로 시작하기</div>
        </li>
      </ul>

      <footer>
        <div>이용약관</div>|<div>개인정보 처리방침</div>|<div>운영정책</div>|
        <div>공지사항</div>
      </footer>
    </div>
  );
};

export default Login;
