'use strict';

var Reflux = require('reflux');

/**
 * Reflux actions, listened to by StudentStore.
 */
var Actions = Reflux.createActions([
    'loadAudits', 'loadAudit', 'createAudit', 'updateAudit', 'deleteAudit',
    'loadReports', 'loadReport', 'createReport', 'updateReport', 'deleteReport',
    'loadQuestions', 'createQuestion',

    'loadData'
]);

module.exports = Actions;
