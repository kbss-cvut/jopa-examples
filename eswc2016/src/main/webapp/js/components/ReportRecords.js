'use strict';

import React from 'react';
import {Button, ButtonToolbar, Input, Panel, Table} from 'react-bootstrap';

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
            report.has_documentation_part.push(record);
        }
        this.props.onChange({has_documentation_part: report.has_documentation_part});
        this.setState({currentRow: null});
    }

    _onAddRecord() {
        this.setState({currentRow: {isNew: true}});
    }

    _onRemoveRecord(record) {
        var report = this.props.report,
            index = report.has_documentation_part.indexOf(record);
        if (index !== -1) {
            report.has_documentation_part.splice(index, 1);
        }
        this.props.onChange({has_documentation_part: report.has_documentation_part});
        this.setState({currentRow: null});
    }


    render() {
        return (
            <Panel header='Records'>

                {this._renderRecords()}

                <div className='row col-xs-1'>
                    <Button bsStyle='info' onClick={this._onAddRecord.bind(this)}>Add Record</Button>
                </div>
            </Panel>
        );
    }

    _renderRecords() {
        var records = this.props.report.has_documentation_part;
        if (this._hasNoRecords()) {
            return <div className='italics form-group'>There are no records, yet.</div>;
        }
        return (
            <Table striped condensed bordered hover>
                <thead>
                <tr>
                    <td className='col-xs-4 centered'>Question</td>
                    <td className='col-xs-5 centered'>Answer</td>
                    <td className='col-xs-3 centered'>Actions</td>
                </tr>
                </thead>
                <tbody>
                {this._renderRecordRows()}
                </tbody>
            </Table>
        );
    }

    _hasNoRecords() {
        var records = this.props.report.has_documentation_part;
        return (!records || records.length == 0) && !this.state.currentRow;
    }

    _renderRecordRows() {
        var records = this.props.report.has_documentation_part,
            currentRow = this.state.currentRow,
            rows = [];
        if (records) {
            for (var i = 0, len = records.length; i < len; i++) {
                if (records[i] === currentRow) {
                    rows.push(<EditedRecordRow key={i} record={records[i]} remove={this._onRemoveRecord.bind(this)}
                                               cancel={this._onEditCancel.bind(this)}
                                               save={this._onEditSave.bind(this)}/>);
                } else {
                    rows.push(<RecordRow key={i} record={records[i]} edit={this._onEditRecord.bind(this)}
                                         remove={this._onRemoveRecord.bind(this)}/>);
                }
            }
        }
        if (currentRow && currentRow.isNew) {
            rows.push(<EditedRecordRow key='new' record={currentRow} remove={this._onRemoveRecord.bind(this)}
                                       cancel={this._onEditCancel.bind(this)} save={this._onEditSave.bind(this)}/>);
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
        var record = this.props.record;
        return (<tr>
            <td>{record.question.has_data_value}</td>
            <td>{record.answer.has_data_value}</td>
            <td>
                <ButtonToolbar>
                    <Button bsStyle='info' onClick={this._onEdit.bind(this)}>Edit</Button>
                    <Button bsStyle='warning' onClick={this._onRemove.bind(this)}>Remove</Button>
                </ButtonToolbar>
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

    componentDidMount() {
        this.refs.question.getInputDOMNode().focus();
    }

    _onRemove() {
        this.props.remove(this.props.record);
    }

    _onSave() {
        var record = this.props.record;
        var fields = ['question', 'answer'];
        for (var i = 0, len = fields.length; i < len; i++) {
            if (!record[fields[i]]) {
                record[fields[i]] = {
                    has_data_value: this.state[fields[i]]
                }
            } else {
                record[fields[i]].has_data_value = this.state[fields[i]];
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
        var removeButton = this.props.record.isNew ? null :
            <Button bsStyle='warning' onClick={this._onRemove.bind(this)}>Remove</Button>;
        return (<tr>
            <td>
                <Input ref='question' type='textarea' rows={2} value={this.state.question}
                       onChange={this._onChange.bind(this)} name='question'/>
            </td>
            <td>
                <Input type='textarea' rows={2} value={this.state.answer} onChange={this._onChange.bind(this)}
                       name='answer'/>
            </td>
            <td className='centered' style={{verticalAlign: 'middle'}}>
                <ButtonToolbar>
                    <Button bsStyle='success' onClick={this._onSave.bind(this)}>Save</Button>
                    <Button onClick={this.props.cancel}>Cancel</Button>
                    {removeButton}
                </ButtonToolbar>
            </td>
        </tr>);
    }
}
