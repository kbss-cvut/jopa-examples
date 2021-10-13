import {applyMiddleware, compose, createStore, Middleware} from "redux";
import thunk, {ThunkMiddleware} from "redux-thunk";
import AppReducers from "../reducer/AppReducers";

const composeEnhancers = (window as any).__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const middlewares: Middleware[] = [thunk as ThunkMiddleware];

const AppStore = createStore(
    AppReducers,
    composeEnhancers(applyMiddleware(...middlewares))
);

export default AppStore;
