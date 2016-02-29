'use strict';

import React from 'react';
import {Button, Modal} from 'react-bootstrap';

export default class DeleteDialog extends React.Component {

    render() {
        return (
            <Modal show={this.props.show} onHide={this.props.onClose}>
                <Modal.Header closeButton>
                    <Modal.Title>
                        Delete item?
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    Are you sure you want to remove this item?
                </Modal.Body>
                <Modal.Footer>
                    <Button bsStyle='warning' onClick={this.props.onSubmit}>Remove</Button>
                    <Button onClick={this.props.onClose}>Cancel</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}
