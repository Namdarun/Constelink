import React from 'react';
import styles from "./CustomerDonateList.module.css"
import { useEffect, useState } from 'react';
import axios from 'axios';
import picture from "../assets/logo/login_kakao.png" 


interface doantionType{
    beneficiaryDisease: string,
    beneficiaryName: string,
    hospitalName: string,
    lastDonationTime: string,
    totalDonationPrice:number
}

const CustomerDonateList = () => {
    const [donations, setDonations] = useState<doantionType[]>([]);    

    useEffect(()=>{
        const accessToken = localStorage.getItem('access_token');
        axios.defaults.headers.common['authorization'] = accessToken;
        axios.get("/member/donations/list?page=1").then(res=>{
            axios.defaults.headers.common={}
            console.log(res.data.donations);
            setDonations(res.data.donations);
        })
    },[])

    const formatDate = (dateString: string): string => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        const hours = date.getHours();
        const minutes = date.getMinutes();
        const amOrPm = hours < 12 ? 'AM' : 'PM';
        return `${year}.${month}.${day}`
      };
    
    return (
        <div className={styles.CustomerDonateList}>
              <div className={styles.fav_title}>내 모금 현황</div>
            <div className={styles.menu_bar}>
                <ul className={styles.menu_list}>
                    <li></li>
                    <li>이름</li>
                    <li>병원</li>
                    <li>병명</li>
                    <li>기부날짜</li>
                    <li>기부금액(원)</li>
                </ul>

                <ul>

                    {
                        donations.map((it,idx)=>{
                            return <li className={styles.amount_log} key={idx}>
                            <div className={styles.log_img}><img src={picture}/></div>
                            <div className={styles.log_item}>{it.beneficiaryName}</div>
                            <div className={styles.log_item}>{it.hospitalName}</div>
                            <div className={styles.log_item}>{it.beneficiaryDisease}</div>
                            <div className={styles.log_item}>{formatDate(it.lastDonationTime+"")}</div>
                            <div className={styles.log_item}> {it.totalDonationPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')}</div>
                        </li>
                        })
                    }
                 
                </ul>


            </div>

        </div>
    );
};

export default CustomerDonateList;
