import {applyMiddleware, createStore, Middleware} from "redux";
import thunk, {ThunkMiddleware} from "redux-thunk";
import AppReducers from "../reducer/AppReducers";

const middlewares: Middleware[] = [thunk as ThunkMiddleware];

const AppStore = createStore(
    AppReducers,
    applyMiddleware(...middlewares)
);

export default AppStore;
