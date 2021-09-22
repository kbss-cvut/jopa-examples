import Constants from './Constants';
import {AxiosResponse} from "axios";
import {Action} from "redux";
import {ThunkDispatch as TDispatch} from "redux-thunk";
import AppModel from "../model/AppModel";

const VALID_IRI_REGEX = /^http:\/\/.+/;

class Util {
    /**
     * Formats the specified date into DD-MM-YY HH:mm
     * @param date The date to format
     */
    formatDate(date: Date) {
        const day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate().toString();
        const month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1).toString();
        const year = (date.getFullYear() % 100).toString();
        const h = date.getHours();
        const hour = h < 10 ? '0' + h : h.toString();
        const minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes().toString();
        return (day + '-' + month + '-' + year + ' ' + hour + ':' + minute);
    }

    /**
     * Extracts report key from location header in the specified Ajax response.
     * @param response Ajax response
     * @return {string} Report key as string
     */
    extractKeyFromLocationHeader(response: AxiosResponse) {
        const location = response.headers['location'];
        if (!location) {
            return '';
        }
        return location.substring(location.lastIndexOf('/') + 1);
    }

    getClassificationClassName(classification: string) {
        for (let key in Constants.RECORD_TYPES) {
            if (!Constants.RECORD_TYPES.hasOwnProperty(key)) {
                continue;
            }
            if (Constants.RECORD_TYPES[key].value === classification) {
                return Constants.RECORD_TYPES[key].className;
            }
        }
        return '';
    }

    /**
     * This is extremely simplistic IRI validator, which just expects the IRI to contain a 'http://' prefix.
     * @param iri The IRI to test
     */
    isIriValid(iri: string) {
        return VALID_IRI_REGEX.test(iri);
    }

    sanitizeArray<T>(arr?: T[] | T | null) {
        return Array.isArray(arr) ? arr : (arr ? [arr] : []);
    }

    /**
     * Extracts query parameter value from the specified query string
     * @param queryString String to extracts params from
     * @param paramName Name of the parameter to extract
     * @return extracted parameter value or undefined if the parameter is not present in the query
     */
    extractQueryParam(queryString: string, paramName: string): string | undefined {
        queryString = decodeURI(queryString);
        const reqexpMatch = queryString.match(new RegExp(paramName + "=([^&]*)"));
        return reqexpMatch ? reqexpMatch[1] : undefined;
    }
}

/**
 * Simple name for Thunk Dispatch, providing the required generic type arguments.
 *
 * Action can be specified.
 */
export type ThunkDispatch<A extends Action = Action> = TDispatch<AppModel, null, Action>;

export default new Util();
