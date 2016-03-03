'use strict';

import React from 'react';
import {Button, ButtonToolbar, Panel} from 'react-bootstrap';
import BasicAuditInfo from './BasicAuditInfo';
import AuditReports from './AuditReports';
import Properties from './Properties';
import Routing from '../util/Routing';
import Actions from '../actions/Actions';

export default class AuditDetail extends React.Component {

    constructor(props) {
        super(props);
    }

    _onChange(e) {
        var change = {};
        change[e.target.name] = e.target.value;
        this.props.actions.change(change);
    }

    _editReport(reportKey) {
        Routing.transitionTo('reports/' + reportKey);
    }

    _removeReport(report, callback) {
        Actions.deleteReport(report.identifier, function () {
            callback();
            Actions.loadAudit(report.documents);
        });
    }

    render() {
        var audit = this.props.audit,
            reportActions = {
                addReport: this.props.actions.add,
                editReport: this._editReport.bind(this),
                removeReport: this._removeReport.bind(this)
            };

        return (
            <Panel header='Audit' bsStyle='info'>
                <BasicAuditInfo audit={audit} onChange={this._onChange.bind(this)}/>

                <AuditReports reports={audit.isDocumentedBy} actions={reportActions}/>

                <Properties properties={audit.properties} onChange={this.props.actions.change}/>

                <div className='row form-group'>
                    <div className='col-xs-3'>
                        <ButtonToolbar>
                            <Button bsStyle='success'
                                    onClick={this.props.actions.save}>{audit.isNew ? 'Create' : 'Save'}</Button>
                            <Button onClick={this.props.actions.cancel}>Cancel</Button>
                        </ButtonToolbar>
                    </div>
                </div>
            </Panel>
        );
    }
}
