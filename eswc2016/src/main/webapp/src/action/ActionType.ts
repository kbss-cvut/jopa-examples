import {Action} from "redux";
import AsyncActionStatus from "./AsyncActionStatus";
import {Message} from "../model/Types";

export interface AsyncAction extends Action {
    status: AsyncActionStatus;
    ignoreLoading?: boolean; // Allows to prevent loading spinner display on async action
}

export interface FailureAction extends Action {
    error: any;
}

export interface AsyncFailureAction extends AsyncAction, FailureAction {
}

export interface AsyncActionSuccess<T> extends AsyncAction {
    payload: T;
}

export interface MessageAction extends Action {
    message: Message;
}

const ActionType = {
    LOAD_AUDITS: "LOAD_AUDITS",
    LOAD_AUDIT: "LOAD_AUDIT",
    CREATE_AUDIT: "CREATE_AUDIT",
    UPDATE_AUDIT: "UPDATE_AUDIT",
    DELETE_AUDIT: "DELETE_AUDIT",

    LOAD_REPORTS: "LOAD_REPORTS",
    LOAD_REPORT: "LOAD_REPORT",
    CREATE_REPORT: "CREATE_REPORT",
    UPDATE_REPORT: "UPDATE_REPORT",
    DELETE_REPORT: "DELETE_REPORT",

    LOAD_QUESTIONS: "LOAD_QUESTIONS",
    CREATE_QUESTION: "CREATE_QUESTION",

    LOAD_DATA: "LOAD_DATA",
    LOAD_SETTINGS: "LOAD_SETTINGS",
    SAVE_SETTINGS: "SAVE_SETTINGS",
    LOAD_PROPERTIES: "LOAD_PROPERTIES",

    PUBLISH_MESSAGE: "PUBLISH_MESSAGE",
    DISMISS_MESSAGE: "DISMISS_MESSAGE"
};

export default ActionType;
