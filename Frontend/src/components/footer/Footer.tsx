import { useDispatch, useSelector } from "react-redux";
import styles from "./Footer.module.css";
import { RootState } from "../../store";
import { useEffect, useRef, useState } from "react";
import Web3 from "web3";
import { walletActions } from "../../store/wallet";
import { authActions } from "../../store/auth";
const Footer: React.FC = () => {
  // 현재 접속한 유저의 metamask address 가져오기
  const walletInfo = useSelector((state: RootState) => state.wallet);
  const connected = useSelector((state: RootState) => state.auth.connected);
  const dispatch = useDispatch();
  useEffect(() => {
    if (walletInfo.web3 === undefined) {
      const getWeb3 = async () => {
        console.log("지갑 가져오기");
        const web3 = await new Web3(window.ethereum);
        dispatch(walletActions.setWeb({ web3 }));
      };
      getWeb3();
    }

    if (connected && walletInfo.web3) {
      const getWallet = async () => {
        try {
          await window.ethereum.request({ method: "eth_requestAccounts" });
        } catch (error) {
          dispatch(authActions.setConnected({ connected: false }));
          throw error;
        }

        const address = await walletInfo.web3?.eth
          .getAccounts()
          .then((res) => res[0]);
        console.log(address, "주소");
        const balance = await walletInfo.web3?.utils
          .fromWei(
            await walletInfo.web3?.eth.getBalance(
              address === undefined ? "" : address
            )
          )
          .slice(0, 5);
        dispatch(
          walletActions.setWallet({ address, balance, connected: true })
        );
      };
      getWallet().catch(() =>
        dispatch(authActions.setConnected({ connected: false }))
      );
    }
  }, [walletInfo.web3, connected]);

  return (
    <div className={styles.footerContainer}>
      <div className={styles.footerContent}>
        <div className={styles.footerContents1}>
          지갑 주소 :{" "}
          {walletInfo.address
            ? walletInfo.address.toString()
            : window.ethereum
            ? "메타마스크 지갑과 연결해 주세요"
            : "메타마스크를 깔아주세요"}
        </div>
        <div className={styles.footerContents2}>
          {connected ? (
            <div className={styles.footerContents2}>
              코인 :{walletInfo.balance} Ether
            </div>
          ) : (
            <div
              className={styles.footer_connect}
              onClick={() => {
                dispatch(authActions.setConnected({ connected: true }));
              }}
            >
              메타 마스크 연결
            </div>
          )}
        </div>
        <div className={styles.footerContents3}>
          <a
            className={styles.footer_link}
            href="/"
            onClick={(e) => {
              e.preventDefault();
              window.open(
                "https://sepolia.etherscan.io/address/0x07A8A469ca0D02049599874580a0aBA76dd34F18",
                "popup",
                "toolbar=no,scrollbars=yes,resizable=yes,status=no,menubar=no,width=900px, height=600px, top=100px,left=350px"
              );
            }}
          >
            기부내역 확인
          </a>
        </div>
      </div>
      <div className={styles.footer_bottom}>
        &copy; Constelink by SSAFY a206 srp
      </div>
    </div>
  );
};

export default Footer;
