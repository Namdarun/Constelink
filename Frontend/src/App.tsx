import { Link, Route, Routes } from "react-router-dom";
import "./App.css";
import HomePage from "./pages/HomePage";
import Login from "./pages/Login";
import MainLayout from "./components/MainLayout";
// import Header from './components/header/Header';
// import Login from './pages/Login';

// 병원 페이지
import HospitalPage from "./pages/HospitalPage";
import BenRegister from "./pages/BenRegister";
import FundRegister from "./pages/FundRegister";
import HosBenList from "./pages/HosBenList";
import HosFundList from "./pages/HosFundList";
import NoticePage from "./pages/NoticePage";
import BenEdit from "./pages/BenEdit";

import NoticeDetail from "./pages/NoticeDetail";
import NoticeEdit from "./pages/NoticeEdit";
import CustomerMyPage from "./pages/CustomerMyPage";
import CustomerMyHome from "./pages/CustomerMyHome";

import RecoveryDiary from "./pages/RecoveryDiary";
import RecoveryDiaryDetail from "./pages/RecoveryDiaryDetail";
import FundMain from "./pages/FundMain";
import FundDetail from "./pages/FundDetail";
import FundPayment from "./pages/FundPayment";
import KakaoPaid from "./pages/KakaoPaid";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "./store";
import HospitalMyPage from "./pages/HospitalMyPage";
import HospitalMyHome from "./pages/HostpitalMyHome";
import FinishFundList from "./pages/FinishFundList";
import { HosBenInfo } from "./models/hospitalmodels";

// interface Data {
//   data: HosBenInfo
// }

function App() {
  const authInfo = useSelector((state: RootState) => state.auth);
  console.log(window.location.pathname);
  return (
    <div className="App2">
      <div className="App">
        <Routes>
          <Route element={<MainLayout />}>
            {/* 홈 페이지 */}
            <Route path="/" element={<HomePage />} />
            {/* 공지사항 페이지 */}
            <Route path="/notice/*" element={<NoticePage />} />

            <Route path="/finish" element={<FinishFundList />} />

            {/* 모금 페이지 */}
            <Route path="/fundmain" element={<FundMain />} />
            <Route path="/fundmain/funddetail/:id" element={<FundDetail />} />
            <Route path="/fundpayment/kakao/:id" element={<FundPayment />} />
            <Route path="/kakao" element={<KakaoPaid />} />

            <Route path="/hospage" element={<HospitalPage />} />
            <Route path="/benregi" element={<BenRegister />} />
            <Route path="/fundregi" element={<FundRegister />} />
            <Route path="/hosbenlist" element={<HosBenList />} />
            <Route path="/benedit" element={<BenEdit />} />
            <Route path="/hosfundlist" element={<HosFundList />} />

            <Route path="/diary" element={<RecoveryDiary />} />
            <Route path="/diarydetail/:id" element={<RecoveryDiaryDetail />} />

            {/* 역할별 마이페이지 설정 */}
            {authInfo.isAuthenticated &&
            (authInfo.role === "MEMBER" || authInfo.role === "ADMIN") ? (
              <Route path="/mypage/*" element={<CustomerMyHome />} />
            ) : authInfo.isAuthenticated && authInfo.role === "HOSPITAL" ? (
              <Route path="/mypage/*" element={<HospitalMyHome />} />
            ) : (
              <Route path="/mypage/*" element={<HospitalMyHome />} />
            )}
          </Route>
          <Route path="/login" element={<Login />} />
        </Routes>
      </div>
    </div>
  );
}
export default App;
