'use strict';

import React from 'react';
import {Input} from 'react-bootstrap';
import DateTimePicker from 'kbss-react-bootstrap-datetimepicker';

export default class BasicAuditInfo extends React.Component {
    constructor(props) {
        super(props);
    }

    _onDateChange(date) {
        var e = {
            target: {
                name: 'date',
                value: Number(date)
            }
        };
        this.props.onChange(e);
    }

    render() {
        var audit = this.props.audit;
        return (
            <div>
                <div className='row'>
                    <div className='col-xs-6'>
                        <Input type='text' label='Title' bsSize='small' value={audit.title}
                               onChange={this.props.onChange} name='title' disabled={this.props.disabled}/>
                    </div>
                </div>
                <div className='row form-group'>
                    <div className='col-xs-6'>
                        <label className='control-label'>Audit date</label>
                        <DateTimePicker inputFormat='DD-MM-YY HH:mm:ss' dateTime={audit.date.toString()}
                                        onChange={this._onDateChange.bind(this)}
                                        inputProps={{title: 'Audit date', bsSize: 'small', disabled: this.props.disabled}}/>
                    </div>
                </div>
            </div>
        );
    }
}
