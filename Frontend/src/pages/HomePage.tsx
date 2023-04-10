import React, { useState, useEffect } from "react";
import styles from "./HomePage.module.css";
import Slider from "react-slick";
import star2 from "../assets/logo/star2.png";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";

import topbanner1 from "../assets/img/topbanner_1.jpg";
import topbanner2 from "../assets/img/topbanner_2.jpg";
import topbanner4 from "../assets/img/topbanner_4.jpg";
import ssafy from "../assets/logo/ssafy_logo.png";
import DonationCard from "../components/cards/DonationCard";
import { DonationData, Statistics } from "../models/donatecard";
import { SliderSettings } from "../models/slidemodel";
import { Navigation, Pagination, Scrollbar, A11y } from "swiper";

import { Swiper, SwiperSlide } from "swiper/react";

import axios from "axios";
// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import "swiper/css/scrollbar";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faSackDollar,
  faHeartPulse,
  faHandHoldingHeart,
} from "@fortawesome/free-solid-svg-icons";
import { faGratipay } from "@fortawesome/free-brands-svg-icons";
import { IconProp } from "@fortawesome/fontawesome-svg-core";
import { useNavigate } from "react-router-dom";
import SideStickyCard from "../components/cards/SideStickyCard";

const images = [topbanner1, topbanner2, topbanner4];
const contents = [
  ["콘스텔링크 Constelink", "블록체인기반, 치료비 모금 플랫폼"],
  ["콘스텔링크 Constelink", "여러분의 관심이 많은이들에게 도움이 됩니다."],
  ["콘스텔링크 Constelink", "블로체인기반, 치료비 모금 플랫폼 당신의 별자리"],
];

const HomePage: React.FC = () => {
  const settings: SliderSettings = {
    dots: true,
    infinite: true,
    speed: 1000,
    slidesToShow: 1,
    slidesToScroll: 1,
    draggable: true,
    touchMove: true,
    fade: true,
    autoplay: true, // 자동 슬라이드 이동을 활성화합니다.
    autoplaySpeed: 4000, // 슬라이드가 자동으로 이동하는 시간을 설정합니다(밀리초).
    arrows: false,
  };

  const [donateCard, setDonateCard] = useState<DonationData[]>([]);
  const [statistics, setStatistics] = useState<Statistics>();
  const navigate = useNavigate();
  useEffect(() => {
    // const accessToken = localStorage.getItem('access_token');

    // axios.defaults.headers.common['authorization'] = accessToken;
    axios
      .get(
        "/fundraising/fundraisings/withbeneficiaryinfo?page=1&size=5&sortBy=START_DATE_DESC&memberId=2"
      )
      .then((res) => {
        console.log(res.data.content);
        // axios.defaults.headers.common={}
        setDonateCard(res.data.content);
      });
  }, []);

  useEffect(() => {
    // const accessToken = localStorage.getItem('access_token');

    // axios.defaults.headers.common['authorization'] = accessToken;
    axios.get("/fundraising/fundraisings/statistics").then((res) => {
      // axios.defaults.headers.common={}
      setStatistics(res.data);
    });
  }, []);

  return (
    <div>
      <div className={styles.relative_box}>
        <div className={styles.Test}>
          {/* 1. 상단 이미지 슬라이바 */}
          <Slider {...settings}>
            {images.map((image, index) => (
              <div className={styles.slide_list} key={index}>
                <div
                  className={styles.slide_item}
                  style={{ backgroundImage: `url(${image})` }}
                >
                  <div className={styles.slide_conbox}>
                    <div className={styles.slide_title}>
                      {contents[index][0]}
                    </div>
                    <div className={styles.slide_content}>
                      {contents[index][1]}
                    </div>
                  </div>
                  <div className={styles.slide_linkbox_out}>
                    <div className={styles.slide_linkbox}>
                      <div className={styles.linkbox_title}>
                        블록체인기반, 치료비 모금 플랫폼
                      </div>
                      <div className={styles.linkbox_sub}>
                        우리의 별자리를 확인해보세요!
                      </div>
                      <div
                        className={styles.linkbox_link}
                        onClick={() => navigate("/fundmain")}
                      >
                        바로가기 {">"}
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ))}
          </Slider>
        </div>
      </div>

      <div className={styles.relative_box2}>
        <div className={styles.Test}>
          <nav className={styles.with_box}>
            <div className={styles.with_box_in}>
              <div className={styles.star_box}>
                <img src={star2} alt="star" />
              </div>
              <div className={styles.with_title}>
                너네 별따러 갈때, 우린 달러가!
              </div>
              <div
                className={styles.with_btn}
                onClick={() => navigate("/fundmain")}
              >
                <span
                  style={{
                    color: "purple",
                    fontWeight: "bold",
                    paddingRight: "3px",
                  }}
                >
                  Constelink
                </span>{" "}
                함께하기 -{">"}
              </div>
            </div>
          </nav>
        </div>
      </div>
      <div>
        {/* 2. 상단 바로가기 바 */}

        <section>
          <SideStickyCard />

          <div className={styles.heal_title_top}>
            <h1>블록체인기반 치료비 모금 플랫폼, Constelink</h1>
            <div>
              직접 돕고 직접 확인하세요. 여러분의 행복, 모두의 행복을 챙기세요.
            </div>
            <div>모든 치료비 기부내역은 투명하게 공개됩니다.</div>
          </div>

          <div className={styles.slide_card}>
            <Swiper
              className={styles.slide_cardItem}
              modules={[Navigation, Pagination, Scrollbar, A11y]}
              spaceBetween={0}
              slidesPerView={4}
              navigation
              pagination={{ clickable: true }}
              onSwiper={(swiper) => {}}
              onSlideChange={() => console.log("slide change")}
            >
              {donateCard.map((it) => {
                return (
                  <SwiperSlide
                    key={it.fundraisingId.toString()}
                    style={{ paddingTop: "10px" }}
                  >
                    <DonationCard data={it} />
                  </SwiperSlide>
                );
              })}
            </Swiper>
          </div>
        </section>

        <div className={styles.addbox}>
          <div className={styles.addbox_item}>
            <div className={styles.addbox_box}>
              <div className={styles.addbox_left}>
                {" "}
                <div className={styles.addbox_ad}>광고</div>
              </div>
              <div className={styles.addbox_right}>
                {" "}
                <div className={styles.addbox_verses}>
                  <img src={ssafy} alt="ssafy" />{" "}
                  <div>
                    <span style={{ fontSize: "14px", color: "white" }}>✖</span>
                    <span style={{ color: "red", marginLeft: "10px" }}>正</span>
                    육점
                  </div>
                </div>
                <div className={styles.addbox_comment}>
                  {" "}
                  최강 SW아카데미 삼성 청년 SW 아카데미와 정육점의 만남
                </div>
                <div className={styles.addbox_finish}>
                  {" "}
                  소프트웨어 최강 SSAFY와의 협약체결{" "}
                  <button
                    onClick={() =>
                      window.open(
                        "https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp",
                        "_blank"
                      )
                    }
                  >
                    확인하기 ➔
                  </button>{" "}
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className={styles.heal_title}>
          <h1>여러분이 만든 여러분의 Constelink</h1>
          <div>여러분의 도움이 있었기에 가능한 일이었습니다.</div>
        </div>

        <div className={styles.result_box}>
          <ul className={styles.result_list}>
            <li className={styles.result_item}>
              <FontAwesomeIcon
                icon={faSackDollar as IconProp}
                className={styles.result_icon}
              />
              <div className={styles.result_content}>
                <div className={styles.content_title}>총 모금액</div>
                <div className={styles.content_curval}>
                  {statistics?.totalAmountedCash
                    .toString()
                    .replace(/\B(?=(\d{3})+(?!\d))/g, ",")}
                  원
                </div>
              </div>
            </li>

            <li className={styles.result_item}>
              {" "}
              <FontAwesomeIcon
                icon={faHeartPulse as IconProp}
                className={styles.result_icon}
              />{" "}
              <div className={styles.result_content}>
                <div className={styles.content_title}>기부 횟수</div>
                <div className={styles.content_curval}>
                  {statistics?.allDonation} 회
                </div>
              </div>
            </li>
            <li className={styles.result_item}>
              {" "}
              <FontAwesomeIcon
                icon={faHandHoldingHeart as IconProp}
                className={styles.result_icon}
              />{" "}
              <div className={styles.result_content}>
                <div className={styles.content_title}>열린 캠페인수</div>
                <div className={styles.content_curval}>
                  {statistics?.totalFundraisings} 회
                </div>
              </div>
            </li>
            <li className={styles.result_item}>
              {" "}
              <FontAwesomeIcon
                icon={faGratipay as IconProp}
                className={styles.result_icon}
                style={{}}
              />{" "}
              <div className={styles.result_content}>
                <div className={styles.content_title}>완료된 캠페인수</div>
                <div className={styles.content_curval}>
                  {statistics?.totalFundraisingsFinished} 회
                </div>
              </div>
            </li>
          </ul>
        </div>

        {/* <div className={styles.footer}> */}

        {/* </div> */}
      </div>
    </div>
  );
};

export default HomePage;
