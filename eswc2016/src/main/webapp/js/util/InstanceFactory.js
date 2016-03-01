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
        var report = {isNew: true, has_documentation_part: []};
        var payload = RouterStore.getTransitionPayload('reports/create');
        if (payload && payload.audit) {
            report.documents = payload.audit;
        } else {
            report.documents = this.createAudit();
        }
        return report;
    }
};
