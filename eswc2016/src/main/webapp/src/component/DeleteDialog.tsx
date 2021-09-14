import React from "react";
import {Button, Modal} from "react-bootstrap";

interface DeleteDialogProps {
    show: boolean;
    onSubmit: () => void;
    onClose: () => void;
}

const DeleteDialog: React.FC<DeleteDialogProps> = props => {
    const {show, onSubmit, onClose} = props;

    return <Modal show={show} onHide={onClose}>
        <Modal.Header closeButton>
            <Modal.Title>
                Delete item?
            </Modal.Title>
        </Modal.Header>
        <Modal.Body>
            Are you sure you want to remove this item?
        </Modal.Body>
        <Modal.Footer>
            <Button variant='warning' onClick={onSubmit}>Remove</Button>
            <Button variant="outline-primary" onClick={onClose}>Cancel</Button>
        </Modal.Footer>
    </Modal>;
};

export default DeleteDialog;
