'use strict';

import React from 'react';

import AuditStore from '../stores/AuditStore';
import Audits from './Audits';
import Mask from './Mask';
import Actions from '../actions/Actions';
import Routing from '../util/Routing';

export default class AuditsController extends React.Component {

    constructor() {
        super();
        this.state = {
            audits: AuditStore.getAudits()
        }
    }

    componentWillMount() {
        Actions.loadAudits();
    }

    componentDidMount() {
        this.unsubscribeData = AuditStore.listen(this._onAuditsLoaded.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onAuditsLoaded(data) {
        if (data.action === Actions.loadAudits) {
            this.setState({audits: data.audits});
        }
    }

    _editAudit(key) {
        Routing.transitionTo('audits/' + key);
    }

    _createAudit() {
        Routing.transitionTo('audits/create');
    }

    _removeAudit(key, callback) {
        Actions.deleteAudit(key, callback);
    }

    render() {
        if (!this.state.audits) {
            return <Mask/>;
        }
        var actions = {
            edit: this._editAudit,
            create: this._createAudit,
            remove: this._removeAudit
        };
        return <Audits audits={this.state.audits} actions={actions}/>;
    }
}
