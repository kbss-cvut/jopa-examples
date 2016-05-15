'use strict';

import React from 'react';
import {Alert} from 'react-bootstrap';
import assign from 'object-assign';

import AuditStore from '../stores/AuditStore';
import AuditDetail from './AuditDetail';
import Actions from '../actions/Actions';
import Mask from './Mask';
import Routing from '../util/Routing';
import InstanceFactory from '../util/InstanceFactory';

export default class AuditController extends React.Component {

    constructor(props) {
        super(props);
        var isNew = props.params.auditKey ? false : true;
        this.state = {
            new: isNew,
            audit: isNew ? InstanceFactory.createAudit() : null,
            showMessage: false,
            message: null
        }
    }

    componentDidMount() {
        this.unsubscribeData = AuditStore.listen(this._onAuditLoaded.bind(this));
        if (!this.state.new) {
            Actions.loadAudit(this.props.params.auditKey);
        }
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onAuditLoaded(data) {
        if (data.action !== Actions.loadAudit) {
            return;
        }
        this.setState({audit: data.audit});
    }

    onChange(change) {
        var audit = this.state.audit;
        assign(audit, change);
        this.setState({audit: audit});
    }

    onCancel() {
        Routing.transitionTo('audits');
    }

    onSave() {
        var audit = this.state.audit;
        if (audit.isNew) {
            Actions.createAudit(audit, this.onSaveSuccess.bind(this), this.onSaveError.bind(this));
        } else {
            Actions.updateAudit(audit, this.onSaveSuccess.bind(this), this.onSaveError.bind(this));
        }
    }

    onAddReport() {
        Routing.transitionTo('reports/create', {audit: this.state.audit});
    }

    onSaveSuccess() {
        this.setState({showMessage: true, message: {type: 'success', text: 'Save successful.'}});
    }

    onSaveError(error) {
        this.setState({showMessage: true, message: {type: 'danger', text: error.message}});
    }

    _hideMessage() {
        this.setState({showMessage: false});
    }

    render() {
        if (!this.state.audit) {
            return <Mask />;
        }
        var actions = {
            change: this.onChange.bind(this),
            cancel: this.onCancel,
            save: this.onSave.bind(this),
            add: this.onAddReport.bind(this)
        };
        return (
            <div>
                <AuditDetail audit={this.state.audit} actions={actions}/>
                {this._renderMessage()}
            </div>);
    }

    _renderMessage() {
        if (this.state.showMessage) {
            return <Alert bsStyle={this.state.message.type} onDismiss={this._hideMessage.bind(this)}
                          dismissAfter={3000}>
                {this.state.message.text}
            </Alert>;
        }
    }
}
