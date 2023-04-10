import styles from "./NoticePage.module.css";
import NoticeCreate from "./NoticeCreate";
import NoticeList from "./NoticeList";
import NoticeDetail from "./NoticeDetail";
import { Routes, Route } from "react-router-dom";
import NoticeEdit from "./NoticeEdit";
const NoticePage: React.FC = () => {
  return (
    <div className={styles.NoticePage}>
      <header className={styles.notice_title}>
        <div>공지사항</div>
      </header>
      <Routes>
        <Route path="/" element={<NoticeList />} />
        <Route path=":id" element={<NoticeDetail />} />
        <Route path=":id/edit" element={<NoticeEdit />} />
        <Route path="/create" element={<NoticeCreate />} />
      </Routes>
    </div>
  );
};

export default NoticePage;
