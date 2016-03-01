'use strict';

import React from 'react';
import {Button, ButtonToolbar, Glyphicon, Input, Modal} from 'react-bootstrap';
import Typeahead from 'react-bootstrap-typeahead';
import TypeaheadResultList from './TypeaheadResultList';

export default class AddRecord extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showAddButton: false,
            question: this.props.question ? this.props.question : null,
            answer: this.props.answer ? this.props.answer : {}
        }
    }

    _onQuestionSelected(question) {
        this.setState({question: question});
    }

    _onAnswerChange(e) {
        var answer = this.state.answer;
        answer.has_data_value = e.target.value;
        this.setState({answer: answer});
    }

    _onQuestionKeyUp(e) {
        var options = this.refs.question.getCurrentOptions();
        if (options.length === 0 && e.target.value !== '') {
            this.setState({showAddButton: true});
        } else {
            this.setState({showAddButton: false});
        }
    }

    render() {
        return <Modal show={this.props.show} onHide={this.props.onClose}>
            <Modal.Header closeButton>
                <Modal.Title>Create record</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <div className='row'>
                    <div className='col-xs-11'>
                        <Typeahead ref='question' className='form-group form-group-sm' formInputOption='id'
                                   optionsButton={true} placeholder='Select existing question'
                                   onOptionSelected={this._onQuestionSelected.bind(this)} filterOption='has_data_value'
                                   displayOption='has_data_value' options={this.state.audits}
                                   onKeyUp={this._onQuestionKeyUp.bind(this)}
                                   customListComponent={TypeaheadResultList}/>
                    </div>
                    <div className='col-xs-1'>{this._renderAddQuestionButton()}</div>
                </div>
                <div className='row'>
                    <div className='col-xs-12'>
                        <Input type='textarea' bsSize='small' value={this.state.answer.has_data_value}
                               onChange={this._onAnswerChange.bind(this)}/>
                    </div>
                </div>
            </Modal.Body>
            <Modal.Footer>
                <ButtonToolbar>
                    <Button bsStyle='success' onClick={this._onSave.bind(this)}>Save</Button>
                    <Button onClick={this.props.onClose}>Cancel</Button>
                </ButtonToolbar>
            </Modal.Footer>
        </Modal>
    }

    _renderAddQuestionButton() {
        if (this.state.showAddButton) {
            return <Button bsStyle='info' onClick={this.addQuestion.bind(this)}>
                <Glyphicon glyph='plus'/>
            </Button>;
        }
        return null;
    }
}
