import { configureStore } from "@reduxjs/toolkit";
import { persistReducer, persistStore } from "redux-persist";
import storage from "redux-persist/lib/storage";
import authReducer from "./auth";
import walletReducedr from "./wallet";

const persistConfig = {
  key: "root",
  storage,
  whitelist: ["role","email","nickname", "isAuthenticated", "profileImg", "connected"],
};
// const walletConfig = {
//   key: "wallet",
//   storage,
//   whitelist: ["connected"],
// };

const persistedReducer = persistReducer(persistConfig, authReducer);

const store = configureStore({
  reducer: {
    auth: persistedReducer,
    wallet: walletReducedr,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false, // redux-persist와 함께 사용하기 위해 직렬화 확인 무시
    }),
});

const persistor = persistStore(store);

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export { store, persistor };
