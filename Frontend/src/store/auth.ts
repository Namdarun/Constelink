import { createSlice } from "@reduxjs/toolkit";
interface AuthState {
  isAuthenticated: boolean;
  nickname: string;
  profileImg: string;
  role: string;
  email: string;
  connected: boolean;
}

const initialAuthState: AuthState = {
  isAuthenticated: false,
  nickname: "",
  profileImg: "",
  role: "",
  email: "",
  connected:false
};

const authSlice = createSlice({
  name: "authentication",
  initialState: initialAuthState,
  reducers: {
    login(state, action) {
      state.isAuthenticated = true;
      state.nickname = action.payload.name;
      state.profileImg = action.payload.profileImg;
      state.role = action.payload.role;
      state.email = action.payload.email;
    },
    logout(state) {
      state.isAuthenticated = false;
      state.role = "";
      state.nickname=""
      state.profileImg=""
      state.email = ""
    },

    update(state, action) {
      console.log(action.payload.name," 확인좀");
      state.nickname = action.payload.name;
    },
    setConnected(state,action) {
      state.connected = action.payload.connected;
    }
    
  },
});

export const authActions = authSlice.actions;

export default authSlice.reducer;
