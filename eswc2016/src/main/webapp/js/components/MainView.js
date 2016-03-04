'use strict';

import React from 'react';
import {Button, Nav, Navbar, NavBrand, NavItem} from 'react-bootstrap';
import {LinkContainer} from 'react-router-bootstrap';
import Actions from '../actions/Actions';
import Constants from '../constants/Constants';
import Routing from '../util/Routing';
import SettingsStore from '../stores/SettingsStore';

export default class MainView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            storage: SettingsStore.getConfig()[Constants.REPOSITORY_TYPE_PARAM]
        }
    }

    componentDidMount() {
        if (!this.state.storage) {
            Actions.loadSetting(Constants.REPOSITORY_TYPE_PARAM);
        }
        this.unsubscribeData = SettingsStore.listen(this._onSettingLoaded.bind(this));
    }

    componentWillUnmount() {
        this.unsubscribeData();
    }

    _onSettingLoaded(data) {
        if (data[Constants.REPOSITORY_TYPE_PARAM]) {
            this.setState({storage: data[Constants.REPOSITORY_TYPE_PARAM]});
        }
    }

    render() {
        return (
            <div>
                <header>
                    <Navbar fluid={true}>
                        <NavBrand>JOPA ESWC 2016</NavBrand>
                        <Nav>
                            <LinkContainer
                                to='audits'><NavItem>Audits</NavItem></LinkContainer>
                            <LinkContainer
                                to='reports'><NavItem>Reports</NavItem></LinkContainer>
                            <LinkContainer
                                to='data'><NavItem>Data</NavItem></LinkContainer>
                            <LinkContainer
                                to='settings'><NavItem>Settings</NavItem></LinkContainer>
                        </Nav>
                    </Navbar>
                </header>
                <div className='row' style={{margin: '-15px 0 0 0'}}>
                    <div className='right'>
                        <Button bsStyle='link' title='View repository type information'
                                onClick={() => {Routing.transitionTo('settings')}}>{this.state.storage}</Button>
                    </div>
                </div>
                <section style={{height: '100%'}}>
                    {this.props.children}
                </section>
            </div>
        );
    }
}
