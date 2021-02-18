import {combineReducers} from "redux";
import ActionType from "../action/ActionType";
import AsyncStatus from "../action/AsyncStatus";
import {formats} from "../component/Data";

function dataFormat(state = formats[0].value, action) {
    if (action.type === ActionType.SELECT_DATA_FORMAT) {
        return action.format;
    }
    return state;
}

function data(state = "", action) {
    if (action.type === ActionType.LOAD_DATA && action.status === AsyncStatus.SUCCESS) {
        return action.payload;
    }
    return state;
}

function students(state = [], action) {
    if (action.type === ActionType.LOAD_STUDENTS && action.status === AsyncStatus.SUCCESS) {
        return action.payload;
    }
    return state;
}

const rootReducer = combineReducers({
    dataFormat,
    data,
    students
});

export default rootReducer;