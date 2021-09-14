import {combineReducers} from "redux";
import AppModel from "../model/AppModel";
import ActionType, {AsyncActionSuccess} from "../action/ActionType";
import AsyncActionStatus from "../action/AsyncActionStatus";

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

const rootReducer = combineReducers<AppModel>({settings});

export default rootReducer;
