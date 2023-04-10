import { Route, Routes } from 'react-router';
import BenRegister from './BenRegister';
import FundRegister from './FundRegister';
import HosBenList from './HosBenList';
import HosFundList from './HosFundList';
import HospitalEditPage from './HospitalEditPage';

import styles from "./HospitalMyHome.module.css"
import HospitalMyPage from './HospitalMyPage';

const HospitalMyHome = () => {
    return (
        <div className={styles.HospitalMyHome}>
            <Routes>
                <Route path='/'  element={<HospitalMyPage/>}/>
                <Route path='/edit'  element={ <HospitalEditPage/>}/>
                <Route path='/hosbenlist'  element={<HosBenList/>}/>
                <Route path='/benregi' element={<BenRegister />} />
                <Route path='/hosfundlist' element={<HosFundList />} />
                <Route path='/fundregi'  element={<FundRegister/>}/>
              
            </Routes>
        </div>
    );
};

export default HospitalMyHome;