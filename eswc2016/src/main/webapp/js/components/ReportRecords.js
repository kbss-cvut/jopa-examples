'use strict';

import React from 'react';
import {Button, Input, Panel, Table} from 'react-bootstrap';

import RecordDetail from './RecordDetail';
import Util from '../util/Util';

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
            <div>
                <Panel header='Records'>

                    {this._renderRecords()}

                    <div className='row col-xs-1'>
                        <Button bsStyle='info' onClick={this._onAddRecord.bind(this)}>Add Record</Button>
                    </div>
                </Panel>
                {this._renderRecordDetail()}
            </div>
        );
    }

    _renderRecords() {
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
            rows = [];
        if (records) {
            for (var i = 0, len = records.length; i < len; i++) {
                rows.push(<RecordRow key={i} record={records[i]} edit={this._onEditRecord.bind(this)}
                                     remove={this._onRemoveRecord.bind(this)}/>);
            }
        }
        return rows;
    }

    _renderRecordDetail() {
        var currentRow = this.state.currentRow;
        if (!currentRow) {
            return null;
        }
        var actions = {
            onClose: this._onEditCancel.bind(this),
            onSave: this._onEditSave.bind(this),
            onRemove: this._onRemoveRecord.bind(this)
        };
        return (<RecordDetail show={true} record={currentRow} actions={actions}/>);
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
        var record = this.props.record,
            classification = record.types ? record.types[0] : '',
            className = 'record-classification ' + Util.getClassificationClassName(classification);
        return (<tr>
            <td style={{verticalAlign: 'middle'}} className={className}>{record.has_question.has_data_value}</td>
            <td style={{verticalAlign: 'middle'}} className={className}>{record.has_answer.has_data_value}</td>
            <td className={'actions ' + className}>
                <Button bsStyle='info' bsSize='small' onClick={this._onEdit.bind(this)}>Edit</Button>
                <Button bsStyle='warning' bsSize='small' onClick={this._onRemove.bind(this)}>Remove</Button>
            </td>
        </tr>);
    }
}
