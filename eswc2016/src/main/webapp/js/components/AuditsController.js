'use strict';

import React from 'react';
import hashHistory from 'react-router';

import AuditStore from '../stores/AuditStore';
import Audits from './Audits';
import Actions from '../actions/Actions';
import Routing from '../util/Routing';

export default class AuditsController extends React.Component {

    constructor() {
        super();
        this.state = {
            audits: AuditStore.getAudits() ? AuditStore.getAudits() : []
        }
    }

    componentWillMount() {
        Actions.loadAudits();
    }

    componentDidMount() {
        this.unsubscribe = AuditStore.listen(this._onAuditsLoaded.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribe();
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

    _removeAudit(key) {
        Actions.removeAudit(key);
    }

    render() {
        var actions = {
            edit: this._editAudit,
            create: this._createAudit,
            remove: this._removeAudit
        };
        return <Audits audits={this.state.audits} actions={actions}/>;
    }
}
