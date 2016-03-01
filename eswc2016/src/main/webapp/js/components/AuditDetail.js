'use strict';

import React from 'react';
import {Button, ButtonToolbar, Panel} from 'react-bootstrap';
import BasicAuditInfo from './BasicAuditInfo';
import Reports from './Reports';

export default class AuditDetail extends React.Component {

    constructor(props) {
        super(props);
    }

    _onChange(e) {
        var change = {};
        change[e.target.name] = e.target.value;
        this.props.actions.change(change);
    }

    _editReport(report) {

    }

    _removeReport(report) {

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

                <Reports reports={audit.reports} actions={reportActions}/>

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
