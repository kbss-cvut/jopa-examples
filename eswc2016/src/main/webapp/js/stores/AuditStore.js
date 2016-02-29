'use strict';

var Reflux = require('reflux');
var request = require('superagent');

var Actions = require('../actions/Actions');

var audits = null;

/**
 * Handles communication with the backend and listens to Actions invoked by the application.
 */
var AuditStore = Reflux.createStore({
    listenables: [Actions],

    onLoadAudits: function (force) {
        if (audits && !force) {
            this.trigger(audits);
        }
        request.get('rest/events').accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                audits = resp.body;
                this.trigger({action: Actions.loadAudits, audits: resp.body});
            }
        }.bind(this));
    },

    onLoadAudit: function (auditKey) {
        request.get('rest/events/' + auditKey).accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                this.trigger({action: Actions.loadAudit, audit: resp.body});
            }
        }.bind(this));
    },

    onCreateAudit: function (audit, onSuccess) {
        request.post('rest/events').type('json').send(audit).end(function (err) {
            if (err) {
                console.log(err);
            } else {
                this.onLoadAudits(true);
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    },

    onDeleteAudit: function (student) {
        request.del('rest/events/' + student.key).end(function (err) {
            if (err) {
                console.log(err);
            } else {
                this.onLoadAudits(true);
            }
        }.bind(this));
    },

    getAudits: function () {
        return audits;
    }
});

module.exports = AuditStore;
