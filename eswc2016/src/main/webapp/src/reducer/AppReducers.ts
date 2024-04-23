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

import {combineReducers} from "redux";
import AppModel from "../model/AppModel";
import ActionType, {AsyncActionSuccess, MessageAction} from "../action/ActionType";
import AsyncActionStatus from "../action/AsyncActionStatus";
import {ReportItem} from "../model/Report";
import Event from "../model/Event";
import {Question} from "../model/Record";
import {Message} from "../model/Types";

function settings(state: { [key: string]: string } = {}, action: AsyncActionSuccess<{ [key: string]: string }>): { [key: string]: string } {
    switch (action.type) {
        case ActionType.LOAD_SETTINGS:
            if (action.status === AsyncActionStatus.SUCCESS) {
                return action.payload;
            }
            return state;
        default:
            return state;
    }
}

function properties(state: string[] = [], action: AsyncActionSuccess<string[]>) {
    if (action.type === ActionType.LOAD_PROPERTIES && action.status === AsyncActionStatus.SUCCESS) {
        return action.payload;
    }
    return state;
}

function reports(state: ReportItem[] = [], action: AsyncActionSuccess<ReportItem[]>) {
    switch (action.type) {
        case ActionType.SAVE_SETTINGS:
            return [];
        case ActionType.LOAD_REPORTS:
            return action.status === AsyncActionStatus.SUCCESS ? action.payload : state;
        default:
            return state;
    }
}

function audits(state: Event[] = [], action: AsyncActionSuccess<Event[]>) {
    if (action.type === ActionType.LOAD_AUDITS && action.status === AsyncActionStatus.SUCCESS) {
        return action.payload;
    }
    return state;
}

function questions(state: Question[] = [], action: AsyncActionSuccess<Question[]>) {
    if (action.type === ActionType.LOAD_QUESTIONS && action.status === AsyncActionStatus.SUCCESS) {
        return action.payload;
    }
    return state;
}

function messages(state: Message[] = [], action: MessageAction): Message[] {
    switch (action.type) {
        case ActionType.PUBLISH_MESSAGE:
            return [...state, action.message];
        case ActionType.DISMISS_MESSAGE:
            const newArr = state.slice(0);
            newArr.splice(newArr.indexOf(action.message), 1);
            return newArr;
        default:
            return state;
    }
}

const rootReducer = combineReducers<AppModel>({audits, messages, properties, questions, reports, settings});

export default rootReducer;
