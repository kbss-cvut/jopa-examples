import {applyMiddleware, compose, createStore} from "redux";
import rootReducer from "../reducer/reducers";
import thunk from "redux-thunk";

const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;

const ReduxStore = createStore(rootReducer, composeEnhancers(applyMiddleware(thunk)));

export default ReduxStore;