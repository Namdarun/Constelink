import React, { PropsWithChildren, useEffect } from "react";
import styles from "./Modal.module.css";

interface ModalDefaultType {
  onClickToggleModal: () => void;
}

// 모달 기본 형식
function Modal({
  onClickToggleModal,
  children,
}: PropsWithChildren<ModalDefaultType>) {
  // 모달이 떠있으면 스크롤 방지하는 함수
  useEffect(() => {
    function disableScroll() {
      document.body.style.overflow = "hidden";
      document.body.style.paddingRight = "22px";
    }
    function enableScroll() {
      document.body.style.overflow = "";
      document.body.style.paddingRight = "";
    }
    // modal이 떠 있을 땐 스크롤 막음
    disableScroll();
    // modal 닫히면 다시 스크롤 가능하도록 함
    return () => enableScroll();
  }, []);

  return (
    <div className={styles.ModalContainer}>
      <div className={styles.DialogBox}>{children}</div>
      <div
        className={styles.Backdrop}
        onClick={(e: React.MouseEvent) => {
          e.preventDefault();
          if (onClickToggleModal) {
            onClickToggleModal();
          }
        }}
      ></div>
    </div>
  );
}

export default Modal;
