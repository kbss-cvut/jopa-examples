'use strict';

import React from 'react';
import {Input, Panel} from 'react-bootstrap';

export default class Settings extends React.Component {
    constructor(props) {
        super(props);
        this.state = {};
    }

    render() {
        return (
            <Panel header='Settings' bsStyle='info'>
            </Panel>
        );
    }
}
