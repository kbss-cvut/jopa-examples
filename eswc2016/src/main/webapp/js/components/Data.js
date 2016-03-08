'use strict';

import React from 'react';
import {Panel, Input} from 'react-bootstrap';

import DataStore from '../stores/DataStore';
import SettingsStore from '../stores/SettingsStore';
import Actions from '../actions/Actions';
import Constants from '../constants/Constants';

/**
 * Displays raw data from the storage.
 */
export default class Data extends React.Component {
    constructor() {
        super();
        this.state = {data: '', format: 'rdfxml', storage: SettingsStore.getConfig()[Constants.REPOSITORY_TYPE_PARAM]}
    }

    componentWillMount() {
        Actions.loadData(this.state.format);
    }

    componentDidMount() {
        this.unsubscribeData = DataStore.listen(this.onDataLoaded.bind(this));
        this.unsubscribeSettings = SettingsStore.listen(this._onSettingLoaded.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribeData();
        this.unsubscribeSettings();
    }

    onDataLoaded(data) {
        this.setState({data: data});
    }

    _onSettingLoaded(data) {
        if (data[Constants.REPOSITORY_TYPE_PARAM]) {
            this.setState({storage: data[Constants.REPOSITORY_TYPE_PARAM]});
        }
    }

    onLoadData() {
        Actions.loadData(this.state.format);
    }

    onFormatSelected(e) {
        var format = e.target.value;
        this.setState({format: format});
        Actions.loadData(format);
    }


    render() {
        return (
            <Panel header={<h3>Repository Content</h3>} bsStyle='info' style={{height: '100%'}}>
                {this._renderDataFormatSelect()}
                <Input type='textarea' rows='20' value={this.state.data}/>
            </Panel>
        );
    }

    _renderDataFormatSelect() {
        if (this.state.storage !== 'sesame') {
            return null;
        }
        return <Input type='select' value={this.state.format} onChange={this.onFormatSelected.bind(this)}>
            <option value='rdfxml'>RDF/XML (Pretty)</option>
            <option value='json'>JSON</option>
            <option value='turtle'>Turtle</option>
        </Input>;
    }
}
