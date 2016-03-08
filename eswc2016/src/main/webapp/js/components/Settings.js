'use strict';

import React from 'react';
import {Alert, Input, Panel} from 'react-bootstrap';

import Actions from '../actions/Actions';
import Constants from '../constants/Constants';
import SettingsStore from '../stores/SettingsStore';

export default class Settings extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            showMessage: false,
            message: null
        };
    }

    componentDidMount() {
        this.unsubscribeData = SettingsStore.listen(this._onSettingLoaded.bind(this));
        Actions.loadSetting(Constants.REPOSITORY_TYPE_PARAM);
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onSettingLoaded(data) {
        if (data[Constants.REPOSITORY_TYPE_PARAM]) {
            this.setState(data);
        }
    }

    _onRepositoryTypeChange(e) {
        if (e.target.value === this.state[Constants.REPOSITORY_TYPE_PARAM]) {
            return;
        }
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
                            <Input type='radio' name={Constants.REPOSITORY_TYPE_PARAM} value='sesame'
                                   checked={this.state[Constants.REPOSITORY_TYPE_PARAM] === 'sesame'} label='Sesame'
                                   onChange={this._onRepositoryTypeChange.bind(this)}/>
                        </div>
                        <div className='col-xs-2'>
                            <Input type='radio' name={Constants.REPOSITORY_TYPE_PARAM} value='owlapi'
                                   checked={this.state[Constants.REPOSITORY_TYPE_PARAM] === 'owlapi'} label='OWL API'
                                   onChange={this._onRepositoryTypeChange.bind(this)}/>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col-xs-12'>
                            <Input type='textarea' rows='10'
                                   value={Constants.STORAGE_INFO[this.state[Constants.REPOSITORY_TYPE_PARAM]]}
                                   disabled/>
                        </div>
                    </div>
                    <div className='row'>
                        <div className='col-xs-12'>
                            More information can be found at <a
                            href='https://github.com/kbss-cvut/jopa-examples/tree/master/eswc2016'>Github</a>.
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
