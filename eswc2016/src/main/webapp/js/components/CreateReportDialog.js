'use strict';

import React from 'react';
import {Button, Input, Modal} from 'react-bootstrap';
import Typeahead from 'react-bootstrap-typeahead';

import Actions from '../actions/Actions';
import AuditStore from '../stores/AuditStore';
import TypeaheadResultList from './TypeaheadResultList';

export default class CreateReportDialog extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            audits: AuditStore.getAudits()
        }
    }

    componentDidMount() {
        this.unsubscribeData = AuditStore.listen(this._onAuditsLoaded.bind(this));
        if (!this.state.audits) {
            Actions.loadAudits();
        }
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onAuditsLoaded(data) {
        if (data.action === Actions.loadAudits) {
            this.setState({audits: data.audits});
        }
    }

    _onAuditSelected(audit) {
        this.props.onSelect(audit);
    }

    render() {
        return <Modal show={this.props.show} onHide={this.props.onClose}>
            <Modal.Header closeButton>
                <Modal.Title>Select audit</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className='col-xs-6'>
                    <Typeahead className='form-group form-group-sm' formInputOption='id' optionsButton={true}
                               placeholder='Select existing audit'
                               onOptionSelected={this._onAuditSelected.bind(this)} filterOption='title'
                               displayOption='title' options={this.state.audits}
                               customListComponent={TypeaheadResultList}/>
                </div>
                <div className='col-xs-1' style={{verticalAlign: 'middle'}}>OR</div>
                <div className='col-xs-5'>
                    <Button bsStyle='info' onClick={() => {this.props.onSelect(null)}}>Create new audit</Button>
                </div>
                <div className='clear'/>
            </Modal.Body>
        </Modal>
    }
}
