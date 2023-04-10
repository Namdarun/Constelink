import { TOKEN_ABI } from "./TOKEN_ABI";
import { FUND_ABI } from "./FUND_ABI";

const TOKEN_CA = "0x368c9316cc25ce537594a63c491db8638e92c65a";
const FUND_CA = "0x8d831dc7da84d527bd18687c3ff4f81d6f7fce47";
const HI_CA = "0xe3534147f7c1ef3ef1706d3d87d92e92c8b09c18";


// const HI_ABI = [
// 	{
// 		"inputs": [],
// 		"name": "sayhi",
// 		"outputs": [
// 			{
// 				"internalType": "string",
// 				"name": "",
// 				"type": "string"
// 			}
// 		],
// 		"stateMutability": "pure",
// 		"type": "function"
// 	}
// ]
// const HiContract = new web3.eth.Contract(HI_ABI, HI_CA);
// HiContract.methods.sayhi().call().then(console.log);

const master = "0xeE06f4E11EB7f5A4F4E61833f2F583fA84Ac4dcc";
const gibuja = "0x8aDFF6e8BFA4795622df2E43966a0Fc975A9E443";

const Web3 = require("web3");
// const ethNet = 'https://rpc.sepolia.org '
const web3 = new Web3(new Web3.providers.HttpProvider('http://localhost:3000'));
window.ethereum.enable();
const accounts = web3.eth.getAccouts();
console.log(accounts);







// const TokenContract = new web3.eth.Contract(TOKEN_ABI, TOKEN_CA);
// const FundContract = new web3.eth.Contract(FUND_ABI, FUND_CA);
// console.log(web3.eth.accounts[0]);

// FundContract.methods.mint(gibuja, 50000).send({from: master}).then(console.log);
// try{
//   console.log("Balance: ");
//   web3.eth.getBalance(gibuja).then(console.log);
// }
// catch(e) {
//   console.log("실패!", e);
// }