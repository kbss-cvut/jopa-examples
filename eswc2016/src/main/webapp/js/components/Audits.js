'use strict';

import React from 'react';
import {Panel, Button, Table} from 'react-bootstrap';

import DeleteDialog from './DeleteDialog';
import Util from '../util/Util';

export default class Audits extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            showDeleteDialog: false
        }
    }

    _showDeleteDialog(auditKey) {
        this.setState({showDeleteDialog: true, currentAuditKey: auditKey});
    }

    _hideDeleteDialog() {
        this.setState({showDeleteDialog: false});
    }

    removeAudit() {
        this.props.actions.remove(this.state.currentAuditKey, this._hideDeleteDialog.bind(this));
    }

    render() {
        return (<Panel header='Audits' bsStyle="info">
            <Table striped bordered condensed hover>
                <thead>
                <tr>
                    <td className="col-xs-7 centered">Title</td>
                    <td className="col-xs-3 centered">Date</td>
                    <td className="col-xs-2 centered">Actions</td>
                </tr>
                </thead>
                <tbody>
                {this._renderAudits()}
                </tbody>
            </Table>
            <Button bsStyle='info' onClick={this.props.actions.create}>New Audit</Button>
            {this._renderDeleteDialog()}
        </Panel>);
    }

    _renderAudits() {
        var audits = this.props.audits,
            rows = [];
        for (var i = 0, len = audits.length; i < len; i++) {
            rows.push(<AuditRow key={audits[i].id} audit={audits[i]} edit={this.props.actions.edit}
                                remove={this._showDeleteDialog.bind(this)}/>);
        }
        return rows;
    }

    _renderDeleteDialog() {
        return <DeleteDialog show={this.state.showDeleteDialog} onSubmit={this.removeAudit.bind(this)}
                             onClose={this._hideDeleteDialog.bind(this)}/>;
    }
}

class AuditRow extends React.Component {

    _editAudit() {
        this.props.edit(this.props.audit.identifier);
    }

    _removeAudit() {
        this.props.remove(this.props.audit.identifier);
    }

    render() {
        var audit = this.props.audit;
        return <tr>
            <td style={{verticalAlign: 'middle'}}>{audit.title}</td>
            <td className='centered' style={{verticalAlign: 'middle'}}>{Util.formatDate(new Date(audit.date))}</td>
            <td className='actions'>
                <Button bsStyle='info' bsSize='small' onClick={this._editAudit.bind(this)}>Edit</Button>
                <Button bsStyle='warning' bsSize='small' onClick={this._removeAudit.bind(this)}>Remove</Button>
            </td>
        </tr>;
    }
}
