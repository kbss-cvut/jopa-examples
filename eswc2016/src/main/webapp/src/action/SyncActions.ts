///
/// JOPA Examples
/// Copyright (C) 2023 Czech Technical University in Prague
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
import ActionType, {AsyncAction, AsyncActionSuccess, AsyncFailureAction} from "./ActionType";
import {Message} from "../model/Types";

export function asyncActionRequest(
    a: Action
): AsyncAction {
    return {...a, status: AsyncActionStatus.REQUEST};
}

export function asyncActionFailure(
    a: Action,
    error: any
): AsyncFailureAction {
    return {
        ...a,
        status: AsyncActionStatus.FAILURE,
        error: Object.assign({}, {type: a.type}, {error}),
    };
}

export function asyncActionSuccessWithPayload<T>(
    a: Action,
    payload: T
): AsyncActionSuccess<T> {
    return {...a, status: AsyncActionStatus.SUCCESS, payload};
}

export function asyncActionSuccess(a: Action): AsyncAction {
    return {...a, status: AsyncActionStatus.SUCCESS};
}

export function publishMessage(message: Message) {
    const m = Object.assign({}, message, {timestamp: Date.now()});
    return {
        type: ActionType.PUBLISH_MESSAGE,
        message: m
    };
}

export function dismissMessage(message: Message) {
    return {
        type: ActionType.DISMISS_MESSAGE,
        message
    };
}

