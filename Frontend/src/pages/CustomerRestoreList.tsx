import React from 'react';
import styles from "./CustomerRestoreList.module.css"
import { useEffect } from 'react';
import axios from 'axios';
const CustomerRestoreList = () => {
    useEffect(() => {
        axios.get("/beneficiary/recoverydiaries/donated?page=1&size=5&sortBy=DATE_DESC&memberId=2").then(res=>{
            console.log(res);
            
        })

    }, [])
    return (
        <div className={styles.CustomerRestoreList}>
            <div className={styles.fav_title}>후원자 치료일지</div>
            <div className={styles.menu_bar}>
                <ul className={styles.menu_list}>
           
                </ul>
            </div>
        </div>
    );
};

export default CustomerRestoreList;