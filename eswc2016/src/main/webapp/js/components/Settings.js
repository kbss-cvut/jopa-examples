'use strict';

import React from 'react';
import {Alert, Input, Panel} from 'react-bootstrap';

import Actions from '../actions/Actions';
import SettingsStore from '../stores/SettingsStore';

var repositoryTypeParam = 'repositoryType';

export default class Settings extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showMessage: false,
            message: null
        };
    }

    componentDidMount() {
        this.unsubscribe = SettingsStore.listen(this._onSettingLoaded.bind(this));
        Actions.loadSetting(repositoryTypeParam);
    }

    componentWillUnmount() {
        this.unsubscribe();
    }

    _onSettingLoaded(data) {
        if (data[repositoryTypeParam]) {
            this.setState(data);
        }
    }

    _onRepositoryTypeChange(e) {
        var change = {};
        change[e.target.name] = e.target.value;
        this.setState(change);
        Actions.saveSettings(change, this.onSaveSuccess.bind(this));
    }

    onSaveSuccess() {
        this.setState({showMessage: true, message: 'Settings saved.'});
    }

    _hideMessage() {
        this.setState({showMessage: false});
    }

    render() {
        return (
            <div>
                <Panel header='Settings' bsStyle='info'>
                    <div className='row'>
                        <div className='col-xs-3'>
                            <label className='control-label'>Repository type</label>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col-xs-2'>
                            <Input type='radio' name={repositoryTypeParam} value='sesame'
                                   checked={this.state[repositoryTypeParam] === 'sesame'} label='Sesame'
                                   onChange={this._onRepositoryTypeChange.bind(this)}/>
                        </div>
                        <div className='col-xs-2'>
                            <Input type='radio' name={repositoryTypeParam} value='owlapi'
                                   checked={this.state[repositoryTypeParam] === 'owlapi'} label='OWL API'
                                   onChange={this._onRepositoryTypeChange.bind(this)}/>
                        </div>
                    </div>
                </Panel>
                {this._renderMessage()}
            </div>
        );
    }

    _renderMessage() {
        if (this.state.showMessage) {
            return <Alert bsStyle='success' onDismiss={this._hideMessage.bind(this)} dismissAfter={2000}>
                {this.state.message}
            </Alert>;
        }
    }
}
