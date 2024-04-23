///
/// JOPA Examples
/// Copyright (C) 2024 Czech Technical University in Prague
///
/// This library is free software; you can redistribute it and/or
/// modify it under the terms of the GNU Lesser General Public
/// License as published by the Free Software Foundation; either
/// version 3.0 of the License, or (at your option) any later version.
///
/// This library is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
/// Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public
/// License along with this library.
///

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
