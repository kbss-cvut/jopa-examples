'use strict';

import React from 'react';
import {Input, Button, Panel} from 'react-bootstrap';
import DateTimePicker from 'kbss-react-bootstrap-datetimepicker';
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

    _onDateChange(value) {
        var change = {
            date: Number(value)
        };
        this.props.actions.change(change);
    }

    _addReport() {

    }

    _editReport(report) {

    }

    _removeReport(report) {

    }

    render() {
        var audit = this.props.audit,
            reportActions = {
                addReport: this._addReport.bind(this),
                editReport: this._editReport.bind(this),
                removeReport: this._removeReport.bind(this)
            };

        return (
            <Panel header='Audit' bsStyle='info'>
                <div className='row'>
                    <div className='col-xs-6'>
                        <Input type='text' label='Title' bsSize='small' value={audit.title}
                               onChange={this._onChange.bind(this)} name='title'/>
                    </div>
                </div>
                <div className='row form-group'>
                    <div className='col-xs-6'>
                        <label className='control-label'>Audit date</label>
                        <DateTimePicker inputFormat='DD-MM-YY HH:mm:ss' dateTime={audit.date.toString()}
                                        onChange={this._onDateChange.bind(this)}
                                        inputProps={{title: 'Audit date', bsSize: 'small'}}/>
                    </div>
                </div>

                <Reports reports={audit.reports} actions={reportActions}/>

                <div className='row form-group'>
                    <div className='col-xs-3'>
                        <Button bsStyle='success'
                                onClick={this.props.actions.save}>{audit.isNew ? 'Create' : 'Save'}</Button>
                        <Button style={{margin: '0 0 0 0.5em'}} onClick={this.props.actions.cancel}>Cancel</Button>
                    </div>
                </div>
            </Panel>
        );
    }
}
