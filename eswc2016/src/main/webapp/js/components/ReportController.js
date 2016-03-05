'use strict';

import React from 'react';
import assign from 'object-assign';
import {Alert} from 'react-bootstrap';

import Actions from '../actions/Actions';
import ReportStore from '../stores/ReportStore';
import Mask from './Mask';
import ReportDetail from './ReportDetail';
import InstanceFactory from '../util/InstanceFactory';
import Routing from '../util/Routing';

export default class ReportController extends React.Component {
    constructor(props) {
        super(props);
        var isNew = !props.params.reportKey;
        this.state = {
            report: isNew ? InstanceFactory.createReport() : null,
            showMessage: false
        }
    }

    componentDidMount() {
        this.unsubscribeData = ReportStore.listen(this._onReportLoaded.bind(this));
        if (this.props.params.reportKey) {
            Actions.loadReport(this.props.params.reportKey);
        }
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onReportLoaded(data) {
        if (data.action === Actions.loadReport) {
            this.setState({report: data.report});
        }
    }

    _onChange(change) {
        var report = this.state.report;
        assign(report, change);
        this.setState({report: report});
    }

    _onSave() {
        var report = this.state.report;
        if (report.isNew) {
            Actions.createReport(report, this.onSaveSuccess.bind(this), this.onSaveError.bind(this));
        } else {
            Actions.updateReport(report, this.onSaveSuccess.bind(this), this.onSaveError.bind(this));
        }
    }

    _onCancel() {
        Routing.transitionTo('reports');
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
        var report = this.state.report;
        if (!report) {
            return <Mask />;
        } else {
            var actions = {
                change: this._onChange.bind(this),
                save: this._onSave.bind(this),
                cancel: this._onCancel
            };
            return <div>
                <ReportDetail report={report} actions={actions}/>
                {this._renderMessage()}
            </div>;
        }
    }

    _renderMessage() {
        if (this.state.showMessage) {
            return <Alert bsStyle={this.state.message.type} onDismiss={this._hideMessage.bind(this)}
                          dismissAfter={2000}>
                {this.state.message.text}
            </Alert>;
        }
    }
}
