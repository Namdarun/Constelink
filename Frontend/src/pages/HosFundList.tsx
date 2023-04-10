import axios from "axios";
import { useEffect, useState } from "react";
import Pagination from "react-js-pagination";
import { useLocation, useNavigate } from "react-router-dom";
import HosFundraisingCard from "../components/cards/HosFundraisingCard";
import { HosFundraisingData } from "../models/hospitalmodels";
import styles from "./HosFundList.module.css";

const HosFundList = (props:object) => {
  const navigator = useNavigate();
  const location = useLocation();

  const [fundraisingData, setFundraisingData] = useState<HosFundraisingData[]>();
  const [page, setPage] = useState(1);
  // const [hospitalId, setHospitalId] = useState(3);
  const size:number = 8;
  const sortBy:string = "UNFINISHED";
  const memberId:number = 0;
  const [totalElements, setTotalElements] = useState(0);
  const URL_PATH : string = "/fundraising/fundraisings/byhospital/self";
  const time:number = new Date().getTime();

  const accessToken = localStorage.getItem("access_token");


  useEffect(() => {

    console.log(accessToken);


    axios.get(URL_PATH, {params : {page, size, sortBy, memberId}, 
    headers: {
      Authorization: accessToken
    }}).then((res) => {
      setFundraisingData(res.data.content);
      setTotalElements(res.data.totalElements)
     })
     .catch((err) => {
      console.log(err);
     })
   }
  , [page]);

  const handlePageChange = (page:number) => {
    setPage(page);
    window.scrollTo(0,0);
  }

  return(
    <>
      <div className={styles.mainWrapper}>
        <div className={styles.mainTitle}>진행 중인 모금</div>
        <div className={`${styles.subtitle_box} ${styles.grid_col_4}`}>
          <li>모금</li>
          <li>수혜자</li>
          <li>남은 기간</li>
          <li>모금 현황</li>
        </div>
        <div className={`${styles.grid_row_8}`}>
            {fundraisingData?.map(data => 
              <HosFundraisingCard key={`fundraising-${data.fundraisingId}`} data={data} time={time}/>
             )}

        </div>
        <div className={styles.sticky_box}>
          <Pagination
            activePage={page}
            itemsCountPerPage={size}
            totalItemsCount={totalElements}
            pageRangeDisplayed={size}
            prevPageText={"‹"}
            nextPageText={"›"}
            onChange={handlePageChange}
          />
        </div>
        
      </div>
    </>
  );
}

export default HosFundList;
