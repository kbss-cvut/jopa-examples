'use strict';

var Reflux = require('reflux');
var request = require('superagent');

var Actions = require('../actions/Actions');
var Util = require('../util/Util').default;

var URL = 'rest/reports';
var URL_WITH_SLASH = URL + '/';

var reports = null;

var ReportStore = Reflux.createStore({
    listenables: [Actions],

    onResetStores: function () {
        reports = null;
    },

    onLoadReports: function () {
        if (reports) {
            this.trigger({action: Actions.loadReports, reports: reports});
        }
        request.get(URL).accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                reports = resp.body;
                this.trigger({action: Actions.loadReports, reports: reports});
            }
        }.bind(this));
    },

    onLoadReport: function (reportKey) {
        request.get(URL_WITH_SLASH + reportKey).accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                this.trigger({action: Actions.loadReport, report: resp.body});
            }
        }.bind(this));
    },

    onCreateReport: function (report, onSuccess, onError) {
        request.post(URL).send(report).type('json').end(function (err, resp) {
            if (err) {
                this._handleError(err, onError);
            } else {
                var key = Util.extractKeyFromLocationHeader(resp);
                this.onLoadReports();
                this.onLoadReport(key);
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    },

    _handleError: function (err, onError) {
        if (onError) {
            try {
                onError(JSON.parse(err.response.text), err);
            } catch (e) {
                console.log(err);
            }
        } else {
            console.log(err);
        }
    },

    onUpdateReport: function (report, onSuccess, onError) {
        request.put(URL_WITH_SLASH + report.identifier).send(report).type('json').end(function (err) {
            if (err) {
                this._handleError(err, onError);
            } else {
                this.onLoadReport(report.identifier);
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    },

    onDeleteReport: function (reportKey, onSuccess) {
        request.del(URL_WITH_SLASH + reportKey).end(function (err) {
            if (err) {
                console.log(err);
            } else {
                this.onLoadReports();
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    },

    getReports: function () {
        return reports;
    }
});

module.exports = ReportStore;
