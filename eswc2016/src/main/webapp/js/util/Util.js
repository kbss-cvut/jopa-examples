'use strict';

import Constants from '../constants/Constants';

var VALID_IRI_REGEX = /^http:\/\/.+/;

class Util {
    /**
     * Formats the specified date into DD-MM-YY HH:mm
     * @param date The date to format
     */
    formatDate(date) {
        var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate().toString();
        var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1).toString();
        var year = (date.getFullYear() % 100).toString();
        var h = date.getHours();
        var hour = h < 10 ? '0' + h : h.toString();
        var minute = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes().toString();
        return (day + '-' + month + '-' + year + ' ' + hour + ':' + minute);
    }

    /**
     * Extracts report key from location header in the specified Ajax response.
     * @param response Ajax response
     * @return {string} Report key as string
     */
    extractKeyFromLocationHeader(response) {
        var location = response.headers['location'];
        if (!location) {
            return '';
        }
        return location.substring(location.lastIndexOf('/') + 1);
    }

    getClassificationClassName(classification) {
        for (var key in Constants.RECORD_TYPES) {
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
    isIriValid(iri) {
        return VALID_IRI_REGEX.test(iri);
    }
}

export default new Util();
