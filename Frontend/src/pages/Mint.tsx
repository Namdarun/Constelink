import { useEffect, useState } from "react";
import { FUND_ABI } from "../web3js/FUND_ABI";
// import { TOKEN_ABI } from "../web3js/TOKEN_ABI";
// import { HI_ABI } from "../web3js/HI_ABI";

import Web3 from "web3";
import { AbiItem } from 'web3-utils';
import { TransactionConfig } from 'web3-core';
import { TransactionReceipt } from 'web3-core/types';

// const master = "0xeE06f4E11EB7f5A4F4E61833f2F583fA84Ac4dcc";
const privateKey = "959577d28acb66ac3987a1a1641d4a3072285a1bf0cdf9d66c6ed8ab795947b8";

const TEST_PUB_FUND_CA = "0x962aDFA41aeEb2Dc42E04586dBa143f2404FD10D";
// const TEST_FUND_CA = "0x8d831dc7da84d527bd18687c3ff4f81d6f7fce47";
// const FUND_CA = "0xa36bd192bfF0e34f09b0592FFb417Dc5A1fDe52F";

const Mint: React.FC = () => {
  
  // const Web3 = require("web3");
  
  // 현재 접속한 유저의 metamask address 가져오기
  const [web3, setWeb3] = useState<Web3 | null>(null);
  const [address, setAddress] = useState<string | null>(null);
  const [contract, setContract] = useState<any | null>(null);
  
  // 계정 주소 불러오고, 펀딩 컨트랙트 연결
  useEffect(() => {
    const detectWeb3 = async () => {
      
      if (typeof window.ethereum !== "undefined") {
        // MetaMask is installed & create an web3 instance
        const provider = window.ethereum;
        await provider.request({ method: "eth_requestAccounts" });
        const web3Instance = new Web3(provider);
        setWeb3(web3Instance);
  
        // Get the user's address
        const accounts = await web3Instance.eth.getAccounts();
        setAddress(accounts[0]);
        
        // Load the contract
        const contractInstance = new web3Instance.eth.Contract(FUND_ABI as AbiItem[], TEST_PUB_FUND_CA);
        setContract(contractInstance); 
      }
    };
    detectWeb3();
  }, []);
  

  // mint컨트랙트 보내기
  async function sendTransactionMint() {
    if (web3) {
      const master = web3.eth.accounts.privateKeyToAccount(privateKey);
      console.log(master);
      const txParams: TransactionConfig = {
        
        from: master.address,
        to: TEST_PUB_FUND_CA,
        gas: 1000000,
        data: contract.methods.mint(address, 10000).encodeABI(),
        nonce: await web3.eth.getTransactionCount(master.address),
        chainId: 11155111,
      };
  
      const signedTX = await master.signTransaction(txParams);
      console.log('이게 signedTX');
      console.log(signedTX.rawTransaction);
      console.log('입니다');
      
      const receipt: TransactionReceipt = await web3.eth.sendSignedTransaction(signedTX.rawTransaction!);
      console.log(`Transaction hash: ${receipt.transactionHash}`);
    } else {
      console.log('Web3 is not available');
    };
  }

  // sendTransaction();
  
  return (
    <>
    {web3 ? (
        <div>
          Web3 version: {web3.version}
          <br />
          {address ? `Your address is ${address}` : "No address detected"}
        </div>
      ) : (
        <div>No web3 detected</div>
      )}
      <button onClick={sendTransactionMint}>
        민팅하기
      </button>
    </>
  )
}

export default Mint;