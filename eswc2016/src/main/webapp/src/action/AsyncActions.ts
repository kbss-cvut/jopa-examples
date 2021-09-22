import ActionType from "./ActionType";
import Util, {ThunkDispatch} from "../util/Util";
import {asyncActionFailure, asyncActionRequest, asyncActionSuccess, asyncActionSuccessWithPayload} from "./SyncActions";
import Event from "../model/Event";
import axios from "axios";

const URL = `${process.env.REACT_APP_SERVER_URL || ""}/rest`;

export function loadSettings(key: string) {
    const action = {type: ActionType.LOAD_SETTINGS};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/configuration?key=${key}`).then(resp => {
            dispatch(asyncActionSuccessWithPayload(action, resp.data));
            return Promise.resolve(resp.data);
        }).catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function saveSettings(settings: {}) {
    const action = {type: ActionType.SAVE_SETTINGS};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.put(`${URL}/configuration`, undefined, {params: settings}).then(() => {
            dispatch(asyncActionSuccess(action));
            return Promise.resolve();
        }).catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function loadData(format: string) {
    const action = {type: ActionType.LOAD_DATA};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/data`, {params: {format}, headers: {"accept": "text/plain"}}).then(resp => {
            dispatch(asyncActionSuccess(action));
            return Promise.resolve(resp.data as string);
        }).catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function loadProperties() {
    const action = {type: ActionType.LOAD_PROPERTIES};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/options/properties`).then(resp => dispatch(asyncActionSuccessWithPayload(action, resp.data)))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    }
}

export function loadReports() {
    const action = {type: ActionType.LOAD_REPORTS};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/reports}`)
            .then(resp => dispatch(asyncActionSuccessWithPayload(action, resp.data)))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function removeReport(id: number) {
    const action = {type: ActionType.DELETE_REPORT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.delete(`${URL}/reports/${id}`)
            .then(() => dispatch(asyncActionSuccess(action)))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function loadAudits() {
    const action = {type: ActionType.LOAD_AUDITS};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/events}`)
            .then(resp => dispatch(asyncActionSuccessWithPayload(action, resp.data)))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function loadAudit(id: number) {
    const action = {type: ActionType.LOAD_AUDIT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/events/${id}`)
            .then(resp => {
                dispatch(asyncActionSuccess(action));
                return Promise.resolve(resp.data);
            })
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function createAudit(audit: Event) {
    const action = {type: ActionType.CREATE_AUDIT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.post(`${URL}/events`, audit)
            .then(resp => {
                dispatch(asyncActionSuccess(action));
                const id = Util.extractKeyFromLocationHeader(resp);
                return dispatch(loadAudit(id));
            })
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function updateAudit(audit: Event) {
    const action = {type: ActionType.UPDATE_AUDIT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.put(`${URL}/events/${audit.identifier!}`, audit)
            .then(() => dispatch(asyncActionSuccess(action)))
            .then(() => dispatch(loadAudit(audit.identifier!)))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}

export function removeAudit(id: Number) {
    const action = {type: ActionType.DELETE_AUDIT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.delete(`${URL}/events/${id}`)
            .then(() => dispatch(asyncActionSuccess(action)))
            .then(() => dispatch(loadAudits))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}
