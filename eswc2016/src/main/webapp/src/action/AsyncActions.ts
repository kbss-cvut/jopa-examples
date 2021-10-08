import ActionType from "./ActionType";
import Util, {ThunkDispatch} from "../util/Util";
import {asyncActionFailure, asyncActionRequest, asyncActionSuccess, asyncActionSuccessWithPayload} from "./SyncActions";
import Event from "../model/Event";
import axios from "axios";
import {Question} from "../model/Record";
import Report from "../model/Report";

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

export function loadReport(id: number) {
    const action = {type: ActionType.LOAD_REPORT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/reports/${id}`)
            .then(resp => {
                dispatch(asyncActionSuccess(action));
                return Promise.resolve(resp.data as Report);
            })
            .catch(err => {
                dispatch(asyncActionFailure(action, err));
                return Promise.reject(err);
            });
    }
}

export function saveReport(report: Report) {
    if (report.isNew) {
        delete report.isNew;
        return createReport(report);
    } else {
        return updateReport(report);
    }
}

function createReport(report: Report) {
    const action = {type: ActionType.CREATE_REPORT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.post(`${URL}/reports`, report)
            .then(resp => {
                asyncActionSuccess(action);
                const key = Util.extractKeyFromLocationHeader(resp);
                dispatch(loadReports);
                return dispatch(loadReport(Number(key)));
            })
            .catch(err => {
                dispatch(asyncActionFailure(action, err));
                return Promise.reject(err);
            });
    };
}

function updateReport(report: Report) {
    const action = {type: ActionType.UPDATE_REPORT};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.put(`${URL}/reports/${report.identifier}`, report)
            .then(() => asyncActionSuccess(action))
            .then(() => dispatch(loadReport(report.identifier!)))
            .catch(err => {
                dispatch(asyncActionFailure(action, err));
                return Promise.reject(err);
            });
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

export function loadQuestions() {
    const action = {type: ActionType.LOAD_QUESTIONS};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.get(`${URL}/questions`)
            .then(questions => dispatch(asyncActionSuccessWithPayload(action, questions)))
            .catch(err => dispatch(asyncActionFailure(action, err)));
    }
}

export function createQuestion(question: Question) {
    const action = {type: ActionType.CREATE_QUESTION};
    return (dispatch: ThunkDispatch) => {
        dispatch(asyncActionRequest(action));
        return axios.post(`${URL}/questions`, question)
            .then(() => dispatch(asyncActionSuccess(action)))
            .then(() => loadQuestions())
            .catch(err => dispatch(asyncActionFailure(action, err)));
    };
}
