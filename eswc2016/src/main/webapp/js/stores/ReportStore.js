'use strict';

var Reflux = require('reflux');
var request = require('superagent');

var Actions = require('../actions/Actions');
var Util = require('../util/Util').default;

var URL = 'rest/reports';
var URL_WITH_SLASH = URL + '/';

var ReportStore = Reflux.createStore({
    listenables: [Actions],

    onLoadReport: function (reportKey) {
        request.get(URL_WITH_SLASH + reportKey).accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                this.trigger({action: Actions.loadReport, report: resp.body});
            }
        }.bind(this));
    },

    onCreateReport: function (report, onSuccess) {
        request.post(URL).data(report).end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                var key = Util.extractKeyFromLocationHeader(resp);
                this.onLoadReport(key);
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    }
});

module.exports = ReportStore;
