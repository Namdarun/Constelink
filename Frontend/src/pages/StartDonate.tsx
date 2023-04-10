import { useEffect, useState } from "react";
import { FUND_ABI } from "../web3js/FUND_ABI";

import Web3 from "web3";
import { AbiItem } from 'web3-utils';
import { TransactionConfig } from 'web3-core';
import { TransactionReceipt } from 'web3-core/types';


const privateKey = "959577d28acb66ac3987a1a1641d4a3072285a1bf0cdf9d66c6ed8ab795947b8";

const TEST_PUB_FUND_CA = "0x962aDFA41aeEb2Dc42E04586dBa143f2404FD10D";
// const TEST_FUND_CA = "0x8d831dc7da84d527bd18687c3ff4f81d6f7fce47";
// const FUND_CA = "0xa36bd192bfF0e34f09b0592FFb417Dc5A1fDe52F";

const StartDonate: React.FC = () => {

  // 현재 접속한 유저의 metamask address 가져오기
  const [web3, setWeb3] = useState<Web3 | null>(null);
  const [address, setAddress] = useState<string | null>(null);
  const [contract, setContract] = useState<any | null>(null);
  
  // 계정 주소 불러오고, 펀딩 컨트랙트 연결
  useEffect(() => {
    const detectWeb3 = async () => {
      // If MetaMask is installed
      if (typeof window.ethereum !== "undefined") {
        // create an web3 instance
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

  // 모금시작하기
  async function sendTransactionStartFunding() {
    if (web3) {
      const master = web3.eth.accounts.privateKeyToAccount(privateKey);
      console.log(master);
      const txParams: TransactionConfig = {
        
        from: master.address,
        to: TEST_PUB_FUND_CA,
        gas: 1000000,
        data: contract.methods.startFund(1, 1111116, 2222229, address).encodeABI(),
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
     <div>
       <button onClick={sendTransactionStartFunding}>
          모금시작하기
       </button>
     </div>
    </>
  )
}

export default StartDonate;