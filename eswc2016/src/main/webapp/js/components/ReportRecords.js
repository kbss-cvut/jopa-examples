'use strict';

import React from 'react';
import {Button, Input, Panel, Table} from 'react-bootstrap';

export default class ReportRecords extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentRow: null
        }
    }

    _onEditRecord(record) {
        this.setState({currentRow: record});
    }

    _onEditCancel() {
        this.setState({currentRow: null});
    }

    _onEditSave(record) {
        var report = this.props.report;
        if (record.isNew) {
            delete record.isNew;
            report.records.push(record);
        }
        this.props.onChange({records: report.records});
        this.setState({currentRow: null});
    }

    _onAddRecord() {
        this.setState({currentRow: {isNew: true}});
    }

    _onRemoveRecord(record) {
        // TODO
    }


    render() {
        return (
            <Panel header='Records'>
                {this._renderRecords()}
                <Button bsStyle='info' onClick={this._onAddRecord.bind(this)}>Add Record</Button>
            </Panel>
        );
    }

    _renderRecords() {
        var records = this.props.report.has_documentation_part;
        if (!records || records.length === 0) {
            return <div className='italics form-group'>There are no records, yet.</div>;
        }
        return (
            <Table striped condensed bordered hover className='form-control'>
                <thead>
                <tr>
                    <td className='col-xs-5 centered'>Question</td>
                    <td className='col-xs-5 centered'>Answer</td>
                    <td className='col-xs-2 centered'>Actions</td>
                </tr>
                </thead>
                <tbody>
                {this._renderRecordRows()}
                </tbody>
            </Table>
        );
    }

    _renderRecordRows() {
        var records = this.props.report.has_documentation_part,
            rows = [];
        for (var i = 0, len = records.length; i < len; i++) {
            if (records[i] === this.state.currentRow) {
                rows.push(<EditedRecordRow key={i} record={records[i]} remove={this._onRemoveRecord.bind(this)}
                                           cancel={this._onEditCancel.bind(this)} save={this._onEditSave.bind(this)}/>);
            } else {
                rows.push(<RecordRow key={i} record={records[i]} edit={this._onEditRecord.bind(this)}
                                     remove={this._onRemoveRecord.bind(this)}/>);
            }
        }
        return rows;
    }
}

class RecordRow extends React.Component {
    constructor(props) {
        super(props);
    }

    _onEdit() {
        this.props.edit(this.props.record);
    }

    _onRemove() {
        this.props.remove(this.props.record);
    }

    render() {
        return (<tr>
            <td>{records[i].question.has_data_value}</td>
            <td>{records[i].answer.has_data_value}</td>
            <td>
                <Button bsStyle='info' onClick={this._onEdit.bind(this)}>Edit</Button>
                <Button bsStyle='warning' onClick={this._onRemove.bind(this)}>Remove</Button>
            </td>
        </tr>);
    }
}

class EditedRecordRow extends React.Component {
    constructor(props) {
        super(props);
        var record = this.props.record;
        this.state = {
            question: record.question ? record.question.has_data_value : '',
            answer: record.answer ? record.answer.has_data_value : ''
        }
    }

    _onRemove() {
        this.props.remove(this.props.record);
    }

    _onSave() {
        var record = this.props.record;
        var fields = ['question', 'answer'];
        for (var key in fields) {
            if (!record[key]) {
                record[key] = {
                    has_data_value: this.state[key]
                }
            } else {
                record[key].has_data_value = this.state[key];
            }
        }
        this.props.save(record);
    }

    _onChange(e) {
        var change = {};
        change[e.target.name] = e.target.value;
        this.setState(change);
    }

    render() {
        return (<tr>
            <td>
                <Input type='textarea' rows={3} value={this.state.question} onChange={this._onChange} name='question'/>
            </td>
            <td>
                <Input type='textarea' rows={3} value={this.state.answer} onChange={this._onChange} name='answer'/>
            </td>
            <td>
                <Button bsStyle='success' onClick={this._onSave.bind(this)}>Save</Button>
                <Button onClick={this.props.cancel}>Cancel</Button>
                <Button bsStyle='warning' onClick={this._onRemove.bind(this)}>Remove</Button>
            </td>
        </tr>);
    }
}
