'use strict';

import React from 'react';
import {Input, Button, Panel} from 'react-bootstrap';
import DateTimePicker from 'kbss-react-bootstrap-datetimepicker';

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

    render() {
        var audit = this.props.audit;

        return (
            <Panel header='Audit' bsStyle='info'>
                <div className='col-xs-12 form-group'>
                    <div className='col-xs-6'>
                        <div className='row'>
                            <Input type='text' label='Title' bsSize='small' value={audit.title}
                                   onChange={this._onChange.bind(this)} name='title'/>
                        </div>
                        <div className='row'>
                            <label className='control-label'>Audit date</label>
                            <DateTimePicker inputFormat='DD-MM-YY HH:mm:ss' dateTime={audit.date.toString()}
                                            onChange={this._onDateChange.bind(this)}
                                            inputProps={{title: 'Audit date', bsSize: 'small'}}/>
                        </div>
                    </div>
                </div>
                <div className='col-xs-12 form-group'>
                    <Button bsStyle='success'
                            onClick={this.props.actions.save}>{audit.isNew ? 'Create' : 'Save'}</Button>
                    <Button style={{margin: '0 0 0 0.5em'}} onClick={this.props.actions.cancel}>Cancel</Button>
                </div>
            </Panel>
        );
    }
}
