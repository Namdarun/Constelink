import React, { useEffect } from 'react';
import styles from "./HospitalEditPage.module.css"
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store';
import { useRef, useState } from 'react';
import axios  from 'axios';
import { useNavigate } from 'react-router-dom';
import { authActions } from '../store/auth';
const HospitalEditPage:React.FC = () => {

    const authNickname = useSelector((state:RootState)=> state.auth.nickname);
    
   
    const [limit, setLimit] = useState<string>("");
    const inputRef = useRef<HTMLInputElement>(null);

    const navigate = useNavigate();
    const nickNameChangeHandler=(e:any)=>{
        setLimit(e.target.value);
    }



    const modifyHandler = ()=>{
        const accessToken = localStorage.getItem('access_token');
        console.log(accessToken);

        axios.defaults.headers.common['authorization'] = accessToken;
        // 회원탈퇴랑, 그거는 리프레시 토큰, 로그아웃할때 , 토크 재발급 받을때만 보내면 된다.
        let editData ={
            nickname: limit,
            profileImg: ""
        }

        console.log(editData);
        
        axios.post("/member/members/modify", editData, {withCredentials:true}).then(res=>{
            console.log("수정이 완료되었습니다.");
            
            console.log(res);
            axios.defaults.headers.common = {};
            navigate("/mypage");
          }).catch((err)=>{

          })
        
    }


    return (
        <div className={styles.HospitalEditPage}>
            
            <header className={styles.modify_title}>병원정보 변경</header>
    
            <div className={styles.modify_notice}>
                 <div className={styles.modify_content}>콘스텔링크에 노출되는 고유한 병원 명칭입니다. 해당 병원명을 10자 이내로 만들어주세요.</div>
            </div>

            <div className={styles.modify_nickname}>
                <div className={styles.modify_nick}>현재 닉네임</div>
                <div className={styles.modify_before}><div className={styles.modify_name}>{authNickname}</div> </div>
            </div>

            <div className={styles.modify_nickname}>
                <div className={styles.modify_nick}>새 닉네임</div>
                <div className={styles.modify_after}><input ref={inputRef} maxLength={10} onChange={nickNameChangeHandler}  placeholder='2자 이상 30자 이하' className={styles.modify_input} type="text" /></div>
            </div>
            

            <div className={styles.modify_finish}>
                {
                    limit.length>2?  <button  className={styles.modify_btn} onClick={modifyHandler} >수정하기</button>   :<button  className={styles.modify_btn} disabled >수정하기</button>
                }
               
                <div className={styles.modify_end}>탈퇴하기</div>
            </div>

        </div>
    );
};

export default HospitalEditPage;