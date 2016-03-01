'use strict';

import React from 'react';

import Actions from '../actions/Actions';
import Mask from './Mask';
import ReportStore from '../stores/ReportStore';
import Reports from './Reports';
import Routing from '../util/Routing';

export default class ReportsController extends React.Component {
    constructor() {
        super();
        this.state = {
            reports: ReportStore.getReports()
        }
    }

    componentWillMount() {
        Actions.loadReports();
    }

    componentDidMount() {
        this.unsubscribe = ReportStore.listen(this._onReportsLoaded.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribe();
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
        Routing.transitionTo('reports/create');
    }

    _removeReport(reportKey, callback) {
        Actions.deleteReport(reportKey, callback);
    }

    render() {
        if (!this.state.reports) {
            return <Mask />;
        }
        var actions = {
            editReport: this._editReport,
            addReport: this._createReport,
            removeReport: this._removeReport
        };
        return <Reports reports={this.state.reports} actions={actions}/>;
    }
}
