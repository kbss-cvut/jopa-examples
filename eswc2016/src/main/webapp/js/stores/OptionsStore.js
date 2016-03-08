'use strict';

var Reflux = require('reflux');
var request = require('superagent');
var Actions = require('../actions/Actions');

var properties = [];

var OptionsStore = Reflux.createStore({

    init: function () {
        this.listenTo(Actions.loadProperties, this._onLoadProperties);
    },

    _onLoadProperties: function () {
        if (properties.length > 0) {
            this.trigger(properties);
        }
        request.get('rest/options/properties').accept('json').end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                if (!resp.body || !resp.body.results) {
                    return;
                }
                var data = resp.body.results.bindings;
                for (var i = 0, len = data.length; i < len; i++) {
                    properties.push({uri: data[i].p.value});
                }
                this.trigger(properties);
            }
        }.bind(this));
    },

    getProperties: function () {
        return properties;
    }
});

module.exports = OptionsStore;
