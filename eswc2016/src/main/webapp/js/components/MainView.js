'use strict';

import React from 'react';
import {Nav, Navbar, NavBrand, NavItem} from 'react-bootstrap';
import {LinkContainer} from 'react-router-bootstrap';

export default class MainView extends React.Component {
    constructor() {
        super();
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
                <section style={{height: '100%'}}>
                    {this.props.children}
                </section>
            </div>
        );
    }
}
