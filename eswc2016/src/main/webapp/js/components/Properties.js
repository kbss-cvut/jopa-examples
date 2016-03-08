'use strict';

import React from 'react';
import {Button, Input, Panel, Table} from 'react-bootstrap';
import Typeahead from 'react-bootstrap-typeahead';
import Actions from '../actions/Actions';
import OptionsStore from '../stores/OptionsStore';
import TypeaheadResultList from './TypeaheadResultList';
import Util from '../util/Util';

/**
 * Represents individual's properties not mapped by the object model.
 */
export default class Properties extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            properties: this.props.properties ? this._flattenProperties() : [],
            currentRow: null
        };
        Actions.loadProperties();
    }

    _flattenProperties() {
        var properties = this.props.properties,
            rows = [];
        for (var property in properties) {
            var values = properties[property];
            for (var i = 0, len = values.length; i < len; i++) {
                rows.push({property: property, value: values[i]});
            }
        }
        return rows;
    }

    _onEditRow(record) {
        this.setState({currentRow: record});
    }

    _onEditCancel() {
        this.setState({currentRow: null});
    }

    _onEditSave(row) {
        if (row.isNew) {
            delete row.isNew;
            this.state.properties.push(row);
        }
        this.setState({currentRow: null, properties: this.state.properties});
        this._propagateChange();
    }

    _propagateChange() {
        var rows = this.state.properties,
            properties = {};
        for (var i = 0, len = rows.length; i < len; i++) {
            if (!properties.hasOwnProperty(rows[i].property)) {
                properties[rows[i].property] = [];
            }
            properties[rows[i].property].push(rows[i].value);
        }
        this.props.onChange({properties: properties});
    }

    _onAddRow() {
        this.setState({currentRow: {isNew: true}});
    }

    _onRemoveRow(record) {
        var properties = this.state.properties,
            index = this.state.properties.indexOf(record);
        if (index !== -1) {
            properties.splice(index, 1);
        }
        this.setState({currentRow: null, properties: properties});
        this._propagateChange();
    }

    render() {
        return (
            <Panel header='Additional properties'>
                <Table striped condensed bordered hover>
                    <thead>
                    <tr>
                        <td className='col-xs-4 centered'>Property</td>
                        <td className='col-xs-5 centered'>Value</td>
                        <td className='col-xs-3 centered'>Actions</td>
                    </tr>
                    </thead>
                    <tbody>
                    {this._renderPropertyRows()}
                    </tbody>
                </Table>
                <Button bsStyle='info' onClick={this._onAddRow.bind(this)}>Add</Button>
            </Panel>
        );
    }

    _renderPropertyRows() {
        var records = this.state.properties,
            currentRow = this.state.currentRow,
            rows = [];
        for (var i = 0, len = records.length; i < len; i++) {
            if (records[i] === currentRow) {
                rows.push(<EditedPropertyRow key={i} record={records[i]} remove={this._onRemoveRow.bind(this)}
                                             cancel={this._onEditCancel.bind(this)}
                                             save={this._onEditSave.bind(this)}/>);
            } else {
                rows.push(<PropertyRow key={i} record={records[i]} edit={this._onEditRow.bind(this)}
                                       remove={this._onRemoveRow.bind(this)}/>);
            }
        }
        if (currentRow && currentRow.isNew) {
            rows.push(<EditedPropertyRow key='new' record={currentRow} remove={this._onRemoveRow.bind(this)}
                                         cancel={this._onEditCancel.bind(this)} save={this._onEditSave.bind(this)}/>);
        }
        return rows;
    }
}

class PropertyRow extends React.Component {
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
            <td className='properties' style={{verticalAlign: 'middle'}}>{record.property}</td>
            <td className='properties' style={{verticalAlign: 'middle'}}>{record.value}</td>
            <td className='actions'>
                <Button bsStyle='info' bsSize='small' onClick={this._onEdit.bind(this)}>Edit</Button>
                <Button bsStyle='warning' bsSize='small' onClick={this._onRemove.bind(this)}>Remove</Button>
            </td>
        </tr>);
    }
}

class EditedPropertyRow extends React.Component {
    constructor(props) {
        super(props);
        var record = this.props.record;
        this.state = {
            property: record.property,
            value: record.value,
            propertyValid: record.property != null && record.property !== ''
        }
    }

    componentDidMount() {
        this.refs.property.focus();
    }

    _onRemove() {
        this.props.remove(this.props.record);
    }

    _onSave() {
        var record = this.props.record;
        record.property = this.state.property;
        record.value = this.state.value;
        this.props.save(record);
    }

    _onPropertyChange(property) {
        this.setState({property: property.uri, propertyValid: true});
    }

    _onPropertyKeyUp(e) {
        this.setState({property: e.target.value, propertyValid: Util.isIriValid(e.target.value)});
    }

    _onChange(e) {
        var change = {};
        change[e.target.name] = e.target.value;
        this.setState(change);
    }

    render() {
        var removeButton = this.props.record.isNew ? null :
                <Button bsStyle='warning' bsSize='small' onClick={this._onRemove.bind(this)}>Remove</Button>,
            properties = OptionsStore.getProperties();
        return (
            <tr>
                <td className='properties'>
                    <div style={{margin: '0 0 -15px 0'}}>
                        <Typeahead ref='property' className='form-group form-group-sm' formInputOption='uri'
                                   optionsButton={true} inputProps={{tabIndex: '1'}}
                                   placeholder='Select property' value={this.state.property}
                                   onOptionSelected={this._onPropertyChange.bind(this)} filterOption='uri'
                                   displayOption='uri' options={properties} onKeyUp={this._onPropertyKeyUp.bind(this)}
                                   customListComponent={TypeaheadResultList}/>
                    </div>
                </td>
                <td className='properties'>
                    <div style={{margin: '0 0 -15px 0'}}>
                        <Input type='text' rows={2} bsSize='small' value={this.state.value}
                               onChange={this._onChange.bind(this)} tabIndex='2'
                               name='value'/>
                    </div>
                </td>
                <td className='actions' style={{verticalAlign: 'middle'}}>
                    <Button bsStyle='success' bsSize='small' onClick={this._onSave.bind(this)}
                            disabled={!this.state.propertyValid}>Save</Button>
                    <Button bsSize='small' onClick={this.props.cancel}>Cancel</Button>
                    {removeButton}
                </td>
            </tr>);
    }
}
