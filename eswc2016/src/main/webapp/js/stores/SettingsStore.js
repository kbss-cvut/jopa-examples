'use strict';

var Reflux = require('reflux');
var request = require('superagent');
var assign = require('object-assign');

var Actions = require('../actions/Actions');

var URL = "rest/configuration";
var PARAM_NAME = "key";
var config = {};

var SettingsStore = Reflux.createStore({
    init: function () {
        this.listenTo(Actions.loadSetting, this.onLoadSetting);
        this.listenTo(Actions.saveSettings, this.onSaveSettings);
    },

    onLoadSetting: function (setting) {
        request.get(URL + "?" + PARAM_NAME + "=" + setting).end(function (err, resp) {
            if (err) {
                console.log(err);
            } else {
                var data = {};
                data[setting] = resp.body;
                assign(config, data);
                this.trigger(data);
            }
        }.bind(this));
    },

    onSaveSettings: function (settings, onSuccess) {
        var url = this._prepareUrl(settings);
        request.put(url).end(function (err) {
            if (err) {
                console.log(err);
            } else {
                Actions.resetStores();
                assign(config, settings);
                this.trigger(settings);
                if (onSuccess) {
                    onSuccess();
                }
            }
        }.bind(this));
    },

    _prepareUrl: function (settings) {
        var url = URL,
            delimiter = '?';
        for (var key in settings) {
            if (!settings.hasOwnProperty(key)) {
                continue;
            }
            url += delimiter + key + '=' + settings[key];
            delimiter = '&';
        }
        return url;
    },

    getConfig: function () {
        return config;
    }
});

module.exports = SettingsStore;
