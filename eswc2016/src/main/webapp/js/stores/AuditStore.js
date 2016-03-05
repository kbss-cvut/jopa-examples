'use strict';

var Reflux = require('reflux');
var request = require('superagent');

var Actions = require('../actions/Actions');
var Util = require('../util/Util').default;

var audits = null;

/**
 * Handles communication with the backend and listens to Actions invoked by the application.
 */
var AuditStore = Reflux.createStore({
    listenables: [Actions],

    onResetStores: function () {
        audits = null;
    },

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

    onCreateAudit: function (audit, onSuccess, onError) {
        request.post('rest/events').type('json').send(audit).end(function (err, resp) {
            if (err) {
                this._handleError(err, onError);
            } else {
                this.onLoadAudits(true);
                this.onLoadAudit(Util.extractKeyFromLocationHeader(resp));
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

    onUpdateAudit: function (audit, onSuccess, onError) {
        request.put('rest/events/' + audit.identifier).type('json').send(audit).end(function (err) {
            if (err) {
                this._handleError(err, onError);
            } else {
                this.onLoadAudit(audit.identifier);
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    },

    onDeleteAudit: function (auditKey, onSuccess) {
        request.del('rest/events/' + auditKey).end(function (err) {
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

    getAudits: function () {
        return audits;
    }
});

module.exports = AuditStore;
