import {Action} from "redux";
import AsyncActionStatus from "./AsyncActionStatus";
import {AsyncAction, AsyncActionSuccess, AsyncFailureAction} from "./ActionType";

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
