'use strict';

var Reflux = require('reflux');
var request = require('superagent');

var Actions = require('../actions/Actions');
var Util = require('../util/Util').default;

var URL = 'rest/questions';

var questions = null;

var QuestionStore = Reflux.createStore({
    init: function () {
        this.listenTo(Actions.loadQuestions, this.onLoadQuestions);
        this.listenTo(Actions.createQuestion, this.onCreateQuestion);
        this.listenTo(Actions.resetStores, this.onResetStores);
    },

    onResetStores: function () {
        questions = null;
    },

    onLoadQuestions: function (force) {
        if (questions && !force) {
            this.trigger({action: Actions.loadQuestions, questions: questions});
            return;
        }
        request.get(URL).accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                questions = resp.body;
                this.trigger({action: Actions.loadQuestions, questions: questions});
            }
        }.bind(this));
    },

    onCreateQuestion: function (question, onSuccess) {
        request.post(URL).send(question).type('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                this.onLoadQuestions(true);
                if (onSuccess) {
                    onSuccess(Number(Util.extractKeyFromLocationHeader(resp)));
                }
            }
        }.bind(this));
    },

    getQuestions: function () {
        return questions;
    }
});

module.exports = QuestionStore;
