'use strict';

import React from 'react';
import {Button, Table, Panel} from 'react-bootstrap';

export default class Reports extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        var content,
            reports = this.props.reports;
        if (reports && reports.length > 0) {
            content = this._renderReportsTable();
        } else {
            content = <div className="form-group italics">There are no reports, yet.</div>;
        }
        return (
            <Panel header='Reports'>
                <div className='row'>
                    <div className='col-xs-12'>
                        {content}
                    </div>
                </div>
                <div className='row'>
                    <div className='col-xs-1'>
                        <Button bsStyle='info' onClick={this.props.actions.addReport}>Add Report</Button>
                    </div>
                </div>
            </Panel>
        );
    }

    _renderReportsTable() {
        var style = {textAlign: 'center'};
        return <Table striped bordered condensed hover>
            <thead>
            <tr>
                <td className='col-xs-3' style={style}>Date</td>
                <td className='col-xs-6' style={style}>Records</td>
                <td className='col-xs-3' style={style}>Actions</td>
            </tr>
            </thead>
            <tbody>
            {this._renderReportRows()}
            </tbody>
        </Table>;
    }

    _renderReportRows() {
        var reports = this.props.reports,
            rows = [];
        for (var i = 0, len = reports.length; i < len; i++) {
            rows.push(<ReportRow key={report.identifier} report={reports[i]} edit={this.props.actions.editReport}
                                 remove={this.props.actions.removeReport}/>);
        }
        return rows;
    }
}

class ReportRow extends React.Component {

    _onEditReport() {
        this.props.edit(this.props.report);
    }

    _onRemoveReport() {
        this.props.remove(this.props.report);
    }

    render() {
        var report = this.props.report;
        return <tr>
            <td>{report.created}</td>
            <td>{report.records ? report.records.length : 0}</td>
            <td className='actions'>
                <Button bsStyle='info' bsSize='small' onClick={this._onEditReport.bind(this)}>Edit</Button>
                <Button bsStyle='warning' bsSize='small' onClick={this.onRemoveReport.bind(this)}>Remove</Button>
            </td>
        </tr>;
    }
}
