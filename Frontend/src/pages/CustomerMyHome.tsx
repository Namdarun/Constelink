import { Route, Routes } from 'react-router';
import CustomerEditPage from './CustomerEditPage';
import CustomerFavoritePage from './CustomerFavoritePage';
import styles from "./CustomerMyHome.module.css"
import CustomerMyPage from './CustomerMyPage';
import CustomerDonateList from './CustomerDonateList';
import CustomerRestoreList from './CustomerRestoreList';
const CustomerMyHome = () => {
    return (
        <div className={styles.CustomerMyHome}>
            <Routes>
                <Route path='/'  element={<CustomerMyPage/>}/>
                <Route path='/edit'  element={   <CustomerEditPage/>}/>
                <Route path='/favorite'  element={<CustomerFavoritePage/>}/>
                <Route path='/donatelist'  element={<CustomerDonateList/>}/>
                <Route path='/restorelist'  element={<CustomerRestoreList/>}/>
            </Routes>
        </div>
    );
};

export default CustomerMyHome;