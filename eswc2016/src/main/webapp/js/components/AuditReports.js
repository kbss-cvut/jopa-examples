'use strict';

import React from 'react';
import {Button, Table, Panel} from 'react-bootstrap';

import DeleteDialog from './DeleteDialog';
import Util from '../util/Util';

export default class AuditReports extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showDeleteDialog: false,
            currentReportKey: null
        }
    }

    _showDeleteDialog(auditKey) {
        this.setState({showDeleteDialog: true, currentReportKey: auditKey});
    }

    _hideDeleteDialog() {
        this.setState({showDeleteDialog: false});
    }

    _removeReport() {
        this.props.actions.removeReport(this.state.currentReportKey, this._hideDeleteDialog.bind(this));
    }

    render() {
        var content,
            reports = this.props.reports;
        if (reports && reports.length > 0) {
            content = this._renderReportsTable();
        } else {
            content = <div className="form-group italics">There are no reports here.</div>;
        }
        return (
            <div>
                <Panel header='Reports' bsStyle={this.props.panelStyle ? this.props.panelStyle : 'default'}>
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
                {this._renderDeleteDialog()}
            </div>
        );
    }

    _renderReportsTable() {
        return <Table striped bordered condensed hover>
            <thead>
            <tr>
                <td className='col-xs-3 centered'>Date created</td>
                <td className='col-xs-3 centered'>Author</td>
                <td className='col-xs-3 centered'>Records</td>
                <td className='col-xs-3 centered'>Actions</td>
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
            rows.push(<ReportRow key={reports[i].identifier} report={reports[i]} edit={this.props.actions.editReport}
                                 remove={this._showDeleteDialog.bind(this)}/>);
        }
        return rows;
    }

    _renderDeleteDialog() {
        return <DeleteDialog show={this.state.showDeleteDialog} onSubmit={this._removeReport.bind(this)}
                             onClose={this._hideDeleteDialog.bind(this)}/>;
    }
}

class ReportRow extends React.Component {

    _onEditReport() {
        this.props.edit(this.props.report.identifier);
    }

    _onRemoveReport() {
        this.props.remove(this.props.report);
    }

    render() {
        var report = this.props.report,
            author = report.hasAuthor ? report.hasAuthor.firstName + ' ' + report.hasAuthor.lastName : '';
        return <tr>
            <td style={{verticalAlign: 'middle'}}>{Util.formatDate(new Date(report.created))}</td>
            <td className='centered'
                style={{verticalAlign: 'middle'}}>{author}</td>
            <td style={{textAlign: 'right', verticalAlign: 'middle'}}>{report.recordCount ? report.recordCount : '?'}</td>
            <td className='actions'>
                <Button bsStyle='info' bsSize='small' onClick={this._onEditReport.bind(this)}>Edit</Button>
                <Button bsStyle='warning' bsSize='small' onClick={this._onRemoveReport.bind(this)}>Remove</Button>
            </td>
        </tr>;
    }
}
