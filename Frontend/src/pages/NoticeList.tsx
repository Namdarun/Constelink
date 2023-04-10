import axios from "axios";
import { useEffect, useState } from "react";
import styles from "./NoticeList.module.css";
import { BoardDetail } from "./../models/boardmodel";
import Pagination from "react-js-pagination";
import "./paging.css";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../store";

const NoticeList = () => {
  const authRole = useSelector((state: RootState) => state.auth.role);
  const [boardList, setBoardList] = useState<BoardDetail[]>([]);
  const [page, setPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const navigate = useNavigate();
  const handlePageChange = (page: number) => {
    console.log(page);
    setPage(page);
  };

  useEffect(() => {
    let params: any = { page: page };
    axios.get("/notices/list", { params }).then((res) => {
      console.log(res);
      setTotalPage(res.data.totalElements);
      setBoardList(res.data.noticeList);
    });
  }, [page]);
  const formatDate = (dateString: string): string => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hours = date.getHours();
    const minutes = date.getMinutes();
    const amOrPm = hours < 12 ? "AM" : "PM";
    const formattedHours = hours % 12 === 0 ? 12 : hours % 12;
    const formattedMinutes = minutes < 10 ? `0${minutes}` : minutes;
    return `${year}년 ${month}월 ${day}일 ${amOrPm} ${formattedHours}:${formattedMinutes}`;
  };
  return (
    <div className={styles.NoticeList}>
      <section className={styles.notice_section}>
        <div className={styles.notice}>
          <div className={styles.notice_info}>공지내역</div>
          <div className={styles.notice_date}>날짜</div>
        </div>
        <ul className={styles.notice_list}>
          {boardList.map((it, idx) => (
            <li
              className={styles.notice_item}
              key={it.id.toString()}
              onClick={() => navigate(`/notice/${it.id}`)}
            >
              <div className={styles.item_nav}>
                {/* <div className={styles.nav_num}>{(8*(page-1))+idx+1}</div> */}

                {it.noticeType === "SYSTEM" ? (
                  <div className={styles.nav_type1}>긴급공지</div>
                ) : (
                  <div className={styles.nav_type2}>일반공지</div>
                )}

                <div className={styles.nav_title}>{it.noticeTitle}</div>
              </div>

              <div className={styles.item_date}>
                <div>{formatDate(it.noticeRegDate)}</div>
              </div>
            </li>
          ))}
        </ul>
      </section>

      <div className={styles.pagination}>
        <Pagination
          activePage={page}
          itemsCountPerPage={8}
          totalItemsCount={totalPage}
          pageRangeDisplayed={8}
          prevPageText={"‹"}
          nextPageText={"›"}
          onChange={handlePageChange}
        />
        {authRole === "ADMIN" ? (
          <div className={styles.create_btn}>
            <button onClick={() => navigate("create")}>글 작성</button>
          </div>
        ) : (
          ""
        )}
      </div>
    </div>
  );
};

export default NoticeList;
