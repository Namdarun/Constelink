import { createSlice } from "@reduxjs/toolkit";
import Web3 from "web3";
interface WalletState {
  address: string;
  balance: string;
  web3: Web3 | undefined;
  // connected: boolean;
}

const initialState: WalletState = {
  address: "",
  balance: "0",
  web3: undefined,
  // connected: false,
};

const walletSlice = createSlice({
  name: "wallet",
  initialState,
  reducers: {
    setWallet(state, action) {
      state.address = action.payload.address;
      state.balance = action.payload.balance;
      // state.connected = action.payload.connected;
    },
    setWeb(state, action) {
      state.web3 = action.payload.web3;
    },
    // setConnected(state, action) {
    //   state.connected = action.payload.connected;
    // },
  },
});

export const walletActions = walletSlice.actions;
export default walletSlice.reducer;
