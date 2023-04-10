import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styles from "./RecoveryDiary.module.css";
import { RecoveryDiaryData } from "./../models/recoveryData";
import axios from "axios";
import Pagination from "react-js-pagination";

const RecoveryDiary: React.FC = () => {
  const [diaryList, setDiaryList] = useState<RecoveryDiaryData[]>([]);
  const [page, setPage] = useState(1);
  const size: number = 6;
  const [totalElements, setTotalElements] = useState(0);
  const navigate = useNavigate();
  const handleCardClick = (beneficiaryId: number) => {
    navigate(`/diarydetail/${beneficiaryId}`);
  };
  // axios 처리
  useEffect(() => {
    axios
      .get("/beneficiary/beneficiaries", {
        params: { page: page, size: size, sortBy: "DIARY_DATE_DESC" },
      })
      .then((res) => {
        console.log(res.data.totalElements);
        setDiaryList(res.data.content);
        setTotalElements(res.data.totalElements);
        console.log(res.data);
        console.log(res.data.totalElements);
      })
      .catch((err) => console.log(err));
  }, [page]);

  const handlePageChange = (page: number) => {
    setPage(page);
  };

  return (
    <div className={styles.container}>
      <div className={styles.item}>
        <div className={styles.searchContainer}>
          <p className={styles.searchBarText}>치료일지</p>
        </div>
        <div className={styles.garoseon}></div>
        <div className={styles.card_box}>
          {/* 결과들 도출 */}
          {diaryList.map((content, index) => (
            <div
              key={content.beneficiaryId}
              className={styles.card_back}
              onClick={() => handleCardClick(content.beneficiaryId)}
            >
              <div className={styles.book_content_box}>
                <div className={styles.book_content}>
                  고객님의 치료일지를 함께 보러 가요!
                </div>
                <div className={styles.book_content2}>
                  성함: {content.beneficiaryName}
                </div>
                <div className={styles.book_content3}>
                  생일: {" "}
                  {new Date(content.beneficiaryBirthday)
                    .toLocaleDateString()
                    .slice(0, -1)}
                </div>
              </div>
             
              <img
                src={content.beneficiaryPhoto}
                onError={(e) => {
                  e.currentTarget.src = "./circleuser.png";
                }}
                alt="profile"
                className={styles.image_inbook}
              />
              
              {/* 선 여러개 */}
              <div className={styles.card_top_margin}>
                <hr></hr>
                <hr></hr>
                <hr></hr>
                <hr></hr>
                <hr></hr>
              </div>
              {/* 타이틀 */}
              <div className={styles.book_title}>
                <div className={styles.title}>
                  {content.beneficiaryName}의 &nbsp; 치료일기
                </div>
                {/* <div className={styles.title}>{content.hospitalName}</div> */}
              </div>
              {/* 카드 커버+안쪽 백그라운드 */}
              <div className={styles.card_cover} key={index}>
                <div className={styles.cardTop}>치료일기</div>

                <div className={styles.book_font}>
                  {content.beneficiaryName} 환자, 병명 :{" "}
                  {content.beneficiaryDisease}
                </div>

                <div className={styles.book_font}>{content.hospitalName}</div>

                <img
                  src={content.beneficiaryPhoto}
                  onError={(e) => {
                    e.currentTarget.src = "./circleuser.png";
                  }}
                  alt="profile"
                  className={styles.image}
                />

                {/* <div className={styles.bottomContent}>
                  <div
                    className={styles.detailButton}
                    onClick={() => handleCardClick(content.beneficiaryId)}
                  >
                    치료일지 열람
                  </div>
                </div> */}
              </div>
            </div>
          ))}
        </div>

        <div className={styles.sticky_pagenation_box}>
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
    </div>
  );
};

export default RecoveryDiary;
