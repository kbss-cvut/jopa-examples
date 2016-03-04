'use strict';

import React from 'react';

import Actions from '../actions/Actions';
import Mask from './Mask';
import ReportStore from '../stores/ReportStore';
import Reports from './Reports';
import CreateReportDialog from './CreateReportDialog';
import Routing from '../util/Routing';

export default class ReportsController extends React.Component {
    constructor() {
        super();
        this.state = {
            reports: ReportStore.getReports(),
            showCreateDialog: false
        }
    }

    componentWillMount() {
        Actions.loadReports();
    }

    componentDidMount() {
        this.unsubscribeData = ReportStore.listen(this._onReportsLoaded.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onReportsLoaded(data) {
        if (data.action === Actions.loadReports) {
            this.setState({reports: data.reports});
        }
    }

    _editReport(reportKey) {
        Routing.transitionTo('reports/' + reportKey);
    }

    _createReport() {
        this.setState({showCreateDialog: true});
    }

    _removeReport(reportKey, callback) {
        Actions.deleteReport(reportKey, callback);
    }

    _closeCreateDialog() {
        this.setState({showCreateDialog: false});
    }

    _onAuditSelected(audit) {
        this.setState({showCreateDialog: false});
        if (audit) {
            Routing.transitionTo('reports/create', {audit: audit});
        } else {
            Routing.transitionTo('audits/create');
        }
    }

    render() {
        if (!this.state.reports) {
            return <Mask />;
        }
        var actions = {
            editReport: this._editReport,
            addReport: this._createReport.bind(this),
            removeReport: this._removeReport
        };
        return (<div>
            <Reports reports={this.state.reports} actions={actions} panelStyle='info'/>
            <CreateReportDialog show={this.state.showCreateDialog} onClose={this._closeCreateDialog.bind(this)}
                                onSelect={this._onAuditSelected.bind(this)}/>
        </div>);
    }
}
