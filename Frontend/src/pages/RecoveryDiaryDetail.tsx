import React, {
  useState,
  useCallback,
  useRef,
  useEffect,

} from "react";
import styles from "./RecoveryDiaryDetail.module.css";
import { useParams, useNavigate } from "react-router-dom";

import Pagination from "react-js-pagination";
import "./paging.css";

import Modal from "../components/Modal/Modal";
import SunEditor from "suneditor-react";
import SunEditorCore from "suneditor/src/lib/core";
import "suneditor/dist/css/suneditor.min.css";
import {
  RecoveryDiaries,
  RecoveryDiaryDetailData,
  RecoveryDiaryCreate,
} from "./../models/recoveryData";
import axios from "axios";
import { useSelector } from "react-redux";
import { RootState } from "../store";

// 리커버리 카드 import 해야함
// 리커버리 카드 -> 생성버튼 -> 모달을 통해 create -> 카드로 생성
// 병원 -> 하단 (생성버튼 목록이동버튼) , 카드클릭 시 수정, 삭제 가능
// 기부자 -> 하단 목록이동버튼, 카드클릭 시 조회

// axios get으로 선택한 diary에서 선택한 카드정보를 가져오고, axios post로 치료일기를 만들 수 있어야 함
const RecoveryDiaryDetail: React.FC = () => {
  const authInfo = useSelector((state:RootState)=>state.auth)



  // 환자정보
  const [treatmentRecords, setTreatmentRecords] =
    useState<RecoveryDiaryDetailData>();

  // 치료카드
  const [recoveryCard, setRecoveryCard] = useState<RecoveryDiaries[]>([]);
  // const { beneficiaryId: id } = useParams<{ beneficiaryId :string }>();
  // const params = useParams<{ id: string }>();
  // const id = Number(params.id);

  // 병원계정일 때 -> 설정 필요
  const [isChecked, setChecked] = useState<boolean>(true);

  // 치료일지 생성
  const inputRef = useRef<HTMLInputElement>(null);
  const { id } = useParams<{ id: string }>();
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [totalGive, setTotalGive] = useState(0);
  // const [createDate, setCreateDate] = useState(0);

  // 모달이 열려있는지 여부 확인
  const [isOpenModal, setOpenModal] = useState<boolean>(false);

  // 카드 클릭했을 때 모달 열고 닫기
  const onClickToggleModal = useCallback(() => {
    setOpenModal(!isOpenModal);
  }, [isOpenModal]);

  // 페이지네이션을 위한 설정
  const [page, setPage] = useState(1);
  const [totalelement, setTotalElement] = useState<number>(0);

  const handlePageChange = (page: number) => {
    setPage(page);
  };

  // Use `id` to get the cardIndex data from the backend

  // 디펜던시 수정
  // axios
  useEffect(() => {
    let params: any = { page: page, size: 6, sortBy: "DATE_DESC" };
    axios
      .get(`/beneficiary/recoverydiaries/${id}`, { params })
      .then((res) => {
        setTreatmentRecords(res.data.beneficiaryInfo);
        setRecoveryCard(res.data.beneficiaryDiaries.content);
        setPage(page);
        setTotalElement(res.data.beneficiaryDiaries.totalElements);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [page, id, setRecoveryCard]);

  // 생성되어 있는 카드를 선택할 때 올바른 정보를 도출
  const [selectedRecordIndex, setSelectedRecordIndex] = useState<any>(null);
  const editor = useRef<SunEditorCore>();
  // The sunEditor parameter will be set to the core suneditor instance when this function is called
  const getSunEditorInstance = (sunEditor: SunEditorCore) => {
    editor.current = sunEditor;
  };

  // 모달 관련 함수 시작
  // 생성된 카드 조회
  const onClickRecord = useCallback((index: number) => {
    setOpenModal(true);
    setChecked(false);
    setSelectedRecordIndex(index);
  }, []);

  // 하단 치료일지생성버튼
  const onClickCreateRecord = useCallback(() => {
    setOpenModal(true);
    setChecked(true);
  }, []);

  // 이미지미리보기(썸네일)
  const [imgPreUrl, setImgPreUrl] = useState("");
  const [image, setImage] = useState(null);
  const handleImage = (e: any) => {
    const file = e.target.files[0];
    setImage(file);
    setImgPreUrl(URL.createObjectURL(file));
    return;
  };

  // 이미지 백에서 가져오기 (완)
  const [imgUrl, setImgUrl] = useState("");
  const getImgUrl = async () => {
    const formData = new FormData();
    if (image) {
      formData.append("file", image);
    }

    return await axios
      .post("/files/saveimg", formData)
      .then((res) => {
        setImgUrl(res.data.fileUrl);
        return res.data.fileUrl;
        // 전체가 끝났을 때 반영
      })
      .catch((err) => {
        console.log(err);
      });
  };

  // 모달 속 생성완료버튼 -> POST
  const onAddRecord = async () => {
    let imgurl2 = await getImgUrl();
    if (!content) {
      alert("내용을 작성해주세요!");
      return;
    } else if (!title) {
      alert("제목을 입력해주세요!");
      return;
    } else if (!imgurl2) {
      alert("이미지를 넣어주세요!");
      return;
    }

    // 카드생성 interface
    const Record: RecoveryDiaryCreate = {
      beneficiaryId: id,
      diaryPhoto: imgurl2,
      diaryTitle: title,
      diaryContent: content,
      diaryAmountSpent: totalGive,
    };

    await axios
      .post(`/beneficiary/recoverydiaries`, Record)
      .then((res) => {
        window.location.replace(`/diarydetail/${id}`);
      })
      .catch((err) => {
        console.log(err);
      });

    setImage(null);
    setTitle("");
    setContent("");
    setTotalGive(0);
    setOpenModal(false);
  };

  // useEffect(() => {
  //   getImgUrl();
  // }, [imgUrl])

  // 입력칸 확인함수
  // function ExampleComponent() {
  //   const inputRef = useRef<HTMLInputElement>(null);

  //   const handleIndexChange = (event: ChangeEvent<HTMLInputElement>) => {
  //   if (event.target.value.length > 10) {
  //   event.target.value = event.target.value.slice(0, 10);
  //   }
  //   console.log(`현재 입력된 글자 수: ${inputRef.current?.value.length}`);
  //   };

  // 날짜스타일 변경함수
  function formatDate(dateString: any) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, "0");
    const day = date.getDate().toString().padStart(2, "0");
    return `${year}년 ${month}월 ${day}일`;
  }

  // 기부액 스타일 변경함수
  function addCommas(num: number | string) {
    const unformatDate = "" + num;
    return unformatDate
      .toString()
      .replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
  }

  // 모달 속 취소버튼
  const onCancelRecord = useCallback(() => {
    setImgPreUrl("");
    setImage(null);
    setContent("");
    setOpenModal(false);
  }, []);

  // 제목 변경 함수
  const handleEditorChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    // console.log(e.target.value);
    if (e.target.value.length > 10) {
      e.target.value = e.target.value.slice(0, 10);
    }
    setTitle(e.target.value);
  };

  // 내용 변경 함수
  const contentChangeHandler = (e: string) => {
    setContent(e);
  };

  // 카드 삭제 함수 -> axios 추가해줘야해
  // const onRemoveRecord = useCallback(() => {
  //   const confirmResult = window.confirm('정말로 삭제하시겠습니까?');
  //   if (confirmResult && selectedRecordIndex !== null) {
  //     setRecoveryCard((prev) => {
  //       const newRecoveryCard = [...prev];
  //       newRecoveryCard.splice(selectedRecordIndex, 1);
  //       return newRecoveryCard;
  //     });
  //     setSelectedRecordIndex(null);
  //     console.log("카드가 삭제됐어요~")
  //     setOpenModal(false);
  //   }
  // }, [selectedRecordIndex, recoveryCard]);

  // 목록으로 이동하는 버튼
  const navigate = useNavigate();

  return (
    <div className={styles.container}>
      <div className={styles.item}>
        {/* 치료일기 세부정보 */}

        <div className={styles.cardIndex}>
          <div className={styles.cardTop}>
            <p className={styles.title}>
              "{treatmentRecords?.beneficiaryName}" 님의 치료일지
            </p>
          </div>
          <div className={styles.cardContent}>
            <div className={styles.imageContainer}>
              <img
                src={treatmentRecords?.beneficiaryPhoto}
                onError={(e) => {
                  e.currentTarget.src = "/circleuser.png";
                }}
                alt="profile"
                className={styles.image}
              />
            </div>
            <div className={styles.patientInfo}>
              <div className={styles.patientInfoItem}>
                <p className={styles.patientInfoTitle}>성명</p>
                <p className={styles.patientInfoContent}>
                  {treatmentRecords?.beneficiaryName}
                </p>
              </div>
              <div className={styles.patientInfoItem}>
                <p className={styles.patientInfoTitle}>생년월일</p>
                <p className={styles.patientInfoContent}>
                  {formatDate(treatmentRecords?.beneficiaryBirthday)}
                </p>
              </div>
              <div className={styles.patientInfoItem}>
                <p className={styles.patientInfoTitle}>병명</p>
                <p className={styles.patientInfoContent}>
                  {treatmentRecords?.beneficiaryDisease}
                </p>
              </div>
              <div className={styles.patientInfoItem}>
                <p className={styles.patientInfoTitle}>병원</p>
                <p className={styles.patientInfoContent}>
                  {treatmentRecords?.hospitalName}
                </p>
              </div>
              <div className={styles.patientInfoItem}>
                <p className={styles.patientInfoTitle}>총 모금액</p>
                <p className={styles.patientInfoContent}>
                  {addCommas(treatmentRecords?.beneficiaryAmountRaised!)}원
                </p>
                {/* <p className={styles.patientInfoContent}>{treatmentRecords.beneficiaryAmountRaised}원</p> */}
              </div>
              <div className={styles.detailButton}>
                {authInfo.role==="HOSPITAL"? <button
                  className={styles.detailCreateButton}
                  onClick={() => onClickCreateRecord()}
                >
                  치료일지 생성
                </button>:""  }
                
                <button
                  className={styles.detailBackButton}
                  onClick={() => navigate(`/diary`)}
                >
                  목록보기
                </button>
              </div>
            </div>
          </div>
        </div>

        <hr className={styles.hr} />

        {/* 생성된 치료일기 */}
        <div>
          {recoveryCard.map((record, index) => (
            <div
              key={index}
              className={styles.recordCard}
              onClick={() => onClickRecord(index)}
            >
              <div className={styles.recordDate}>
                {formatDate(record.diaryRegisterDate)}
              </div>
              {/* {imgUrl && <img src={record.diaryPhoto} className={styles.recordImage} alt='nononono'/>} */}
              {
                <div className={styles.recordImage}>
                  <img
                    className={styles.recordImage}
                    src={record.diaryPhoto}
                    onError={(e) => {
                      e.currentTarget.src = "/diaryImg1.jpg";
                    }}
                    alt="diaryPhoto"
                  />
                </div>
              }
              {/* {record.diaryPhoto && <img src={record.diaryPhoto} className={styles.recordImage} alt={imgPreUrl}/>} */}
              <div className={styles.recordIndex}>
                {record.diaryTitle.length > 10
                  ? `${record.diaryTitle.substring(0, 10)}...`
                  : record.diaryTitle}
              </div>
              <div
                className={styles.recordContent}
                dangerouslySetInnerHTML={{
                  __html:
                    record.diaryContent && record.diaryContent.length > 10
                      ? `${record.diaryContent.substring(0, 30)}...`
                      : record.diaryContent || "",
                }}
              ></div>
            </div>
          ))}
        </div>

        {/* 치료일기생성버튼 */}
      </div>
      <div className={styles.pagination}>
        <Pagination
          activePage={page}
          itemsCountPerPage={6}
          totalItemsCount={totalelement}
          pageRangeDisplayed={6}
          prevPageText={"‹"}
          nextPageText={"›"}
          onChange={handlePageChange}
        />
      </div>
      {/* 모달 확인, 조회 */}
      {/* 취소는 모달 우상단 */}
      {isOpenModal && selectedRecordIndex !== null && (
        <Modal onClickToggleModal={onClickToggleModal}>
          <div className={styles.modalTop}>
            <div className={styles.modalText}>치료일기 조회</div>
            <button
              className={styles.modalClose}
              onClick={() => onCancelRecord()}
            ></button>
          </div>
          <img
            src={recoveryCard[selectedRecordIndex].diaryPhoto}
            onError={(e) => {
              e.currentTarget.src = "/diaryImg1.jpg";
            }}
            alt={imgPreUrl}
            className={styles.modalImage}
          />
          <hr className={styles.modalHr} />
          <div className={styles.modalInfoTitle}>
            {recoveryCard[selectedRecordIndex].diaryTitle}
          </div>
          <div className={styles.modalInfoContent}>
            <div className={styles.modalInfoDate}>
              {formatDate(recoveryCard[selectedRecordIndex].diaryRegisterDate)}
            </div>
            <div
              className={styles.modalContent}
              dangerouslySetInnerHTML={{
                __html: recoveryCard[selectedRecordIndex].diaryContent || "",
              }}
            />
            <div className={styles.modalButton}>
              {/* <button className={styles.modalButtonItem} onClick={() => onEditRecord()}>수정</button> */}
            </div>
            {
              <div className={styles.modalButton}>
                <button
                  className={styles.modalButtonItem}
                  onClick={() => onCancelRecord()}
                >
                  확인
                </button>
              </div>
            }
          </div>
        </Modal>
      )}

      {/* 생성버튼 클릭 -> 치료일지 생성 */}
      {isOpenModal && isChecked === true && (
        <Modal onClickToggleModal={onClickToggleModal}>
          <div className={styles.modalTopCreate}>
            <div className={styles.modalText}>치료일기 생성</div>
            <button
              className={styles.modalClose}
              onClick={() => onCancelRecord()}
            ></button>
          </div>
          {/* 제목입력칸 */}
          <div>
            <input
              type="text"
              placeholder={"10자까지 입력이 가능합니다."}
              ref={inputRef}
              className={styles.modalInfoTitle}
              onChange={handleEditorChange}
            />
            <div className={styles.inputIndexCheck}>{title.length}/10</div>
          </div>
          {/* 이미지 입력 */}
          <div className={styles.imgInput}>
            {/* 이미지 선택하면 해당 이미지 띄우고 없으면 기본 이미지 */}
            {imgPreUrl ? (
              <div className={styles.withImgCircle}>
                <img className={styles.benImg} src={imgPreUrl} alt="" />
              </div>
            ) : (
              <div className={styles.imgCircle} />
            )}
            <label htmlFor="imgUp">
              <div className={styles.plusIcon} />
            </label>
            <input
              type="file"
              id="imgUp"
              className={styles.inputFile}
              accept="image/jpg, image/png, image/jpeg"
              onChange={handleImage}
            />
          </div>
          <hr className={styles.modalHr} />
          <div className={styles.modalInfo}>
            <SunEditor
              getSunEditorInstance={getSunEditorInstance}
              lang="ko"
              width="250px"
              height="180px"
              autoFocus={false}
              onChange={contentChangeHandler}
              setDefaultStyle="font-family:Hahmlet;color:darkgrey;font-size: 20px;"
              placeholder="환자의 치료일지를 적어주세요"
              setOptions={{
                buttonList: [["bold", "underline", "table", "fontColor"]],
              }}
              // SunEditor 끝
            />
            <div className={styles.modalButton}>
              <button className={styles.modalButtonItem} onClick={onAddRecord}>
                생성완료
              </button>
              <button
                className={styles.modalButtonItem}
                onClick={onCancelRecord}
              >
                취소
              </button>
            </div>
          </div>
        </Modal>
      )}
    </div>
  );
};
export default RecoveryDiaryDetail;
