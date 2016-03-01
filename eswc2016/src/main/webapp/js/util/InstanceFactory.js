'use strict';

var RouterStore = require('../stores/RouterStore');

module.exports = {

    createAudit: function () {
        return {
            isNew: true,
            date: (Date.now() / 1000) * 1000
        };
    },

    createReport: function () {
        var report = {isNew: true};
        var payload = RouterStore.getTransitionPayload('reports/create');
        if (payload && payload.audit) {
            report.audit = payload.audit;
        } else {
            report.audit = this.createAudit();
        }
        return report;
    }
};
