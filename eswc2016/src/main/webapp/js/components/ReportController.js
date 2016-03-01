'use strict';

import React from 'react';
import assign from 'object-assign';

import Actions from '../actions/Actions';
import ReportStore from '../stores/ReportStore';
import Mask from './Mask';
import ReportDetail from './ReportDetail';
import InstanceFactory from '../util/InstanceFactory';

export default class ReportController extends React.Component {
    constructor(props) {
        super(props);
        var isNew = !props.params.reportKey;
        this.state = {
            report: isNew ? InstanceFactory.createReport() : null
        }
    }

    componentDidMount() {
        this.unsubscribe = ReportStore.listen(this._onReportLoaded.bind(this));
        if (this.props.params.reportKey) {
            Actions.loadReport(this.props.params.reportKey);
        }
    }

    componentWillUnmount() {
        this.unsubscribe();
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

    render() {
        var report = this.state.report;
        if (!report) {
            return <Mask />;
        } else {
            return <ReportDetail report={report} onChange={this._onChange.bind(this)}/>;
        }
    }
}
