// import { useState } from "react";
import styles from "./FundMain.module.css";
import DonationCard from "../components/cards/DonationCard";
import { DonationData } from "../models/donatecard";
import { useEffect, useCallback, useRef, useMemo } from "react";
import axios from "axios";
import { useState } from "react";
import Pagination from "react-js-pagination";
import "./paging.css";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";
import Footer from "../components/footer/Footer";
interface CategoryData {
  id: number;
  categoryName: string;
}

const FundMain: React.FC = () => {
  const [categories, setCategories] = useState<CategoryData[]>([]);
  const [selected, setSelected] = useState<string>("0");
  const categoryBox = useRef<HTMLDivElement | null>(null);
  const [scrollOn, setScrollOn] = useState(true);
  const [page, setPage] = useState(1);
  const [totalPage, setTotalPage] = useState(0);
  const navigate = useNavigate();
  const [campaignList, setCampaignList] = useState<DonationData[]>([]);

  const handlePageChange = (page: number) => {
    setPage(page);
  };

  const getCategories = useCallback(async () => {
    const response: CategoryData[] = await axios
      .get("fundraising/categories", { params: { size: 100 } })
      .then((res) => {
        return res.data.content;
      });

    setCategories(response);
  }, []);

  const prevent = useCallback((e: Event) => {
    e.preventDefault();
  }, []);

  //처음에 카테고리 가져오기
  useEffect(() => {
    getCategories();
  }, [getCategories]);

  // Wheel 이벤트 막기
  useEffect(() => {
    if (scrollOn === false) {
      document.body.addEventListener("wheel", prevent, { passive: false });
    } else {
      document.body.removeEventListener("wheel", prevent, false);
    }
  }, [scrollOn, prevent]);

  // page 바꾸거나 category바꾸면 데이터 다시 받아오기 (selected는 선택한 category)
  useEffect(() => {
    if (selected === "0") {
      axios
        .get("fundraising/fundraisings", {
          params: { page: page, size: 16, sortBy: "UNFINISHED" },
        })
        .then((res) => {
          setTotalPage(res.data.totalElements);
          setCampaignList(res.data.content);
        });
    } else {
      axios
        .get("fundraising/fundraisings/bycategory", {
          params: { page: page, size: 16, categoryId: selected },
        })
        .then((res) => {
          setTotalPage(res.data.totalElements);
          setCampaignList(res.data.content);
        });
    }
  }, [page, selected]);

  // categoryList는 다시 불러오기 방지.
  const categoryList = useMemo(() => {
    let aa: string = selected;
    return (
      <div className={styles.scroll_box}>
        {categories.map((category) => {
          return (
            <div
              key={`category-${category.id}`}
              className={`${styles.category_box}`}
            >
              <input
                type="radio"
                id={`category-${category.id}`}
                name="category"
                className={``}
                value={category.id}
                onClick={(e) => {
                  if (e.currentTarget.value === aa) {
                    setSelected("0");
                    aa = "0";
                    e.currentTarget.checked = false;
                  } else {
                    aa = e.currentTarget.value;
                    setSelected(e.currentTarget.value);
                  }
                  setPage(1);
                }}
              ></input>
              <label
                htmlFor={`category-${category.id}`}
                className={`${
                  styles[`box_color_${Math.floor(Math.random() * 5) + 1}`]
                }`}
              >
                {category.categoryName}
              </label>
            </div>
          );
        })}
      </div>
    );
  }, [categories]);

  return (
    <div className={styles.mainWrapper}>
      <div className={styles.mainBanner}>
        <div className={styles.bannerTitle}>
          무한한 하늘에 별자리를 만들어 주세요.
        </div>
        <div className={styles.bannerSubTitle}>
          그대의 작은 손길 한번이 누군가에겐 인생의 전환점이 됩니다
        </div>
      </div>

      <div className={styles.fundWrapper}>
        <div
          className={styles.blockWrapper}
          onMouseOver={() => {
            setScrollOn(false);
          }}
          onMouseOut={() => {
            setScrollOn(true);
          }}
        >
          <div
            ref={categoryBox}
            className={`${styles.categoryWrapper}`}
            onWheel={(e) => {
              if (categoryBox.current)
                categoryBox.current.scrollLeft += 2.5 * e.deltaY;
            }}
          >
            {categoryList}
          </div>
        </div>

        <div className={styles.cardsTitle}>Constelink Dreams</div>
        <div className={styles.cardsWrapper}>
          {campaignList.map((it, idx) => {
            return (
              <div
                className={styles.cardWrapper}
                key={idx}
                // onClick={() =>
                //   navigate(`/fundmain/funddetail/${it.fundraisingId}`)
                // }
              >
                <DonationCard data={it} />
              </div>
            );
          })}
        </div>

        {campaignList.length === 0 ? (
          <>
            <div className={styles.cardsWrapper_1}>
              <div className={styles.empty_signal}>
                <FontAwesomeIcon icon={faMagnifyingGlass} />
              </div>
              <div className={styles.empty_ment}>
                관련된 카테고리의 치료모금이 존재하지 않습니다.
              </div>
            </div>
          </>
        ) : (
          <>
            <div className={styles.pagination}>
              <Pagination
                activePage={page}
                itemsCountPerPage={16}
                totalItemsCount={totalPage}
                pageRangeDisplayed={16}
                prevPageText={"‹"}
                nextPageText={"›"}
                onChange={handlePageChange}
              />
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default FundMain;
