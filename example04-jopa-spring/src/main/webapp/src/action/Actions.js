import AsyncStatus from "./AsyncStatus";
import ActionType from "./ActionType";
import axios from "axios";

export function asyncRequest(action) {
    return Object.assign({}, action, {status: AsyncStatus.REQUEST});
}

export function asyncSuccess(action) {
    return Object.assign({}, action, {status: AsyncStatus.SUCCESS});
}

export function asyncSuccessWithPayload(action, payload) {
    return Object.assign({}, action, {status: AsyncStatus.SUCCESS, payload});
}

export function asyncFailure(action, error) {
    return Object.assign({}, action, {status: AsyncStatus.FAILURE, error});
}

export function selectDataFormat(format) {
    return {type: ActionType.SELECT_DATA_FORMAT, format};
}

export function loadData() {
    const action = {type: ActionType.LOAD_DATA};
    return (dispatch, getState) => {
        dispatch(asyncRequest(action));
        return axios.get(`rest/data?format=${getState().dataFormat}`, {headers: {accept: "text/plain"}})
            .then(resp => resp.data)
            .then(data => dispatch(asyncSuccessWithPayload(action, data)))
            .catch(error => {
                dispatch(asyncFailure(action, error));
                return "";
            });
    };
}

export function loadStudents() {
    const action = {type: ActionType.LOAD_STUDENTS};
    return dispatch => {
        dispatch(asyncRequest(action));
        return axios.get("rest/students")
            .then(resp => resp.data)
            .then(students => dispatch(asyncSuccessWithPayload(action, students)))
            .catch(error => dispatch(asyncFailure(action, error)));
    }
}

export function createStudent(student) {
    const action = {type: ActionType.CREATE_STUDENT};
    return dispatch => {
        dispatch(asyncRequest(action));
        return axios.post("rest/students", student)
            .then(() => {
                dispatch(asyncSuccess(action));
                dispatch(loadData());
                return dispatch(loadStudents());
            })
            .catch(error => dispatch(asyncFailure(action, error)));
    }
}

export function deleteStudent(student) {
    const action = {type: ActionType.DELETE_STUDENT};
    return dispatch => {
        dispatch(asyncRequest(action));
        return axios.delete(`rest/students/${student.key}`)
            .then(() => {
                dispatch(asyncSuccess(action));
                dispatch(loadData());
                return dispatch(loadStudents());
            })
            .catch(error => dispatch(asyncFailure(action, error)));
    }
}