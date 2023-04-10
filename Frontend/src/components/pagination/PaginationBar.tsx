import './PaginationBar.css';
import Pagination from "react-js-pagination";
import {useState} from 'react';

interface Props {
  totalPages: Number,
  currentPage : Number,
  displayPage: Number
}
const PaginationBar:React.FC<Props> =({totalPages,currentPage, displayPage}) => {

    const [page, setPage] = useState(1);
    const [totalPage, setTotalPage] = useState(0);
    const handlePageChange = (page:number) => {
      console.log(page);
      setPage(page);
    };
    return (
        <div className='pagenation'>
        <Pagination
            activePage={page}
            itemsCountPerPage={4}
            totalItemsCount={64}
            pageRangeDisplayed={5}
            prevPageText={"‹"}
            nextPageText={"›"}
            onChange={handlePageChange}
          />
        </div>
    );
};

export default PaginationBar;