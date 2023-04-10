import axios from 'axios';
import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styles from './NoticeDetail.module.css'
import { BoardDetail } from './../models/boardmodel';
import { useSelector } from 'react-redux';
import { RootState } from '../store';

const NoticeDetail = () => {
     const [contents, setContents] = useState<BoardDetail| null>(null);
     const navigate= useNavigate();
     const authRole = useSelector((state: RootState)=> state.auth.role);
     
     const { id } = useParams<{ id: string }>();
     useEffect(()=> {
        axios.get(`/notices/detail?id=${id}`).then(res=>{
            console.log(res.data);
            setContents(res.data)
            
        })
     }, [id])

     const formatDate = (dateString: string): string => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const hours = date.getHours();
        const minutes = date.getMinutes();
        const amOrPm = hours < 12 ? 'AM' : 'PM';
        const formattedHours = hours % 12 === 0 ? 12 : hours % 12;
        const formattedMinutes = minutes < 10 ? `0${minutes}` : minutes;
        return `${year}년 ${month}월 ${day}일 ${amOrPm} ${formattedHours}:${formattedMinutes}`;
      };
     
    return (
        <div className={styles.NoticeDetail}>
      
            <section className={styles.board_detail}>
                <header className={styles.detail_title}>
                    <div className={styles.title_left}>
                        {contents?.noticeType==='COMMON' ? <div className={styles.nav_type2}>일반 공지</div>:   <div className={styles.nav_type1}>긴급공지</div>}
                       
                        <div className={styles.left_content}>{contents?.noticeTitle}</div>
                    </div>

                    <div className={styles.right_content}>{formatDate(contents?.noticeRegDate||"")}  </div>
                </header>
                <div className={styles.detail_content}
                    dangerouslySetInnerHTML={{ __html: contents?.noticeContent||"" }}>
                </div>

            </section>


            <div className={styles.write_finish} >

                 {
                    authRole==="ADMIN" ?     <button className={styles.modi_btn} onClick={()=> navigate(`/notice/${contents?.id}/edit`)}>글 수정</button>: ""
                }
             
          
            <button className={styles.back_btn} onClick={()=> navigate('/notice/')}>글 목록</button>
            </div>
        </div>
    );
};

export default NoticeDetail;