import React, { useRef, useState }from 'react';
import styles from "./CustomerEditPage.module.css"
import { useSelector } from 'react-redux';
import { RootState } from '../store';
import { useNavigate } from 'react-router-dom';
import axios  from 'axios';
const CustomerEditPage:React.FC = () => {

    const authNickname = useSelector((state:RootState)=> state.auth.nickname);
    
    const [limit, setLimit] = useState<string>("");
    const inputRef = useRef<HTMLInputElement>(null);

    const navigate = useNavigate();
    const nickNameChangeHandler=(e:any)=>{
        setLimit(e.target.value);
    }
    const modifyHandler = ()=>{
        // const accessToken = localStorage.getItem('access_token');

        let editData ={
            nickname: limit,
            profileImg: ""
        }
        // axios.defaults.headers.common['authorization'] = accessToken;
        axios.post("/member/members/modify", editData).then(res=>{
            alert("수정이 완료되었습니다.")
            navigate("/mypage");
          }).catch((err)=>{
            console.log(err);
          })
        
    }



    
    
    return (
        <div className={styles.CustomerEditPage}>
            
            <header className={styles.modify_title}>개인정보 변경</header>
    
            <div className={styles.modify_notice}>
                 <div className={styles.modify_content}>콘스텔링크에 노출되는 고유한 사용자 명칭입니다. 원하는 닉네임을 10자 이내로 만들어주세요.</div>
            </div>

            <div className={styles.modify_nickname}>
                <div className={styles.modify_nick}>현재 닉네임</div>
                <div className={styles.modify_before}><div className={styles.modify_name}>{authNickname}</div> </div>
            </div>

            <div className={styles.modify_nickname}>
                <div className={styles.modify_nick}>새 닉네임</div>
                <div className={styles.modify_after}><input ref={inputRef} minLength={2} maxLength={10} onChange={nickNameChangeHandler}  placeholder='2자 이상 10자 이하' className={styles.modify_input} type="text" /></div>
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

export default CustomerEditPage;