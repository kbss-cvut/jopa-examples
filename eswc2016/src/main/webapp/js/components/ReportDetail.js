'use strict';

import React from 'react';
import {Button, Panel, Input} from 'react-bootstrap';
import assign from 'object-assign';

import BasicAuditInfo from './BasicAuditInfo';
import ReportRecords from './ReportRecords';
import Util from '../util/Util';

export default class ReportDetail extends React.Component {
    constructor(props) {
        super(props);
    }

    _onAuditChange(change) {
        var audit = this.props.report.audit;
        assign(audit, change);
        this.props.onChange({audit: audit});
    }

    render() {
        var report = this.props.report;
        return (
            <Panel header='Report' bsStyle='info'>
                <Panel header='Audit info'>
                    <BasicAuditInfo audit={report.audit} onChange={this._onAuditChange.bind(this)}/>
                </Panel>

                {this._renderOriginInfo()}

                <ReportRecords report={report} onChange={this.props.onChange}/>
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
        return <div className='col-xs-12 italics'>{text}</div>;
    }
}
