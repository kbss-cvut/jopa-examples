'use strict';

import React from 'react';
import {Button, ButtonToolbar, Panel, Input} from 'react-bootstrap';
import assign from 'object-assign';

import BasicAuditInfo from './BasicAuditInfo';
import ReportRecords from './ReportRecords';
import Properties from './Properties';
import Util from '../util/Util';

export default class ReportDetail extends React.Component {
    constructor(props) {
        super(props);
    }

    _onAuditChange(change) {
        var audit = this.props.report.audit;
        assign(audit, change);
        this.props.actions.change({audit: audit});
    }

    render() {
        var report = this.props.report;
        return (
            <Panel header='Report' bsStyle='info'>
                <Panel header='Audit info'>
                    <BasicAuditInfo audit={report.documents} onChange={this._onAuditChange.bind(this)}/>
                </Panel>

                {this._renderOriginInfo()}

                <ReportRecords report={report} onChange={this.props.actions.change}/>

                <Properties properties={report.properties} onChange={this.props.actions.change}/>

                <ButtonToolbar>
                    <Button bsStyle='success' onClick={this.props.actions.save}>Save</Button>
                    <Button onClick={this.props.actions.cancel}>Cancel</Button>
                </ButtonToolbar>
            </Panel>
        );
    }

    _renderOriginInfo() {
        var report = this.props.report,
            text;
        if (report.isNew) {
            return null;
        }
        text = 'Created on ' + Util.formatDate(new Date(report.created));
        if (report.author) {
            text += ' by ' + report.author.firstName + ' ' + report.author.lastName;
        }
        text += '.';
        return <div className='row'>
            <div className='col-xs-12 italics'>{text}</div>
        </div>;
    }
}
