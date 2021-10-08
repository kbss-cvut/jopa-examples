import Event from "../../model/Event";
import React, {useEffect} from "react";
import {Button, Col, Modal, Row} from "react-bootstrap";
import {useDispatch, useSelector} from "react-redux";
import Select from "react-select";
import {ThunkDispatch} from "../../util/Util";
import {loadAudits} from "../../action/AsyncActions";
import AppModel from "../../model/AppModel";
import {SelectOption} from "../../model/Types";

interface CreateReportDialogProps {
    show: boolean;
    onClose: () => void;
    onSelect: (audit: Event | null) => void;
}

const CreateReportDialog: React.FC<CreateReportDialogProps> = props => {
    const {show, onClose, onSelect} = props;
    const dispatch: ThunkDispatch = useDispatch();
    useEffect(() => {
        dispatch(loadAudits());
    }, [dispatch]);
    const audits = useSelector((state: AppModel) => state.audits);
    const options = audits.map(a => ({label: a.title, value: a.id}));
    const onOptionSelect = (opt: SelectOption | null) => {
        if (opt == null) {
            onSelect(null);
        }
        onSelect(audits.find(a => a.id = opt!.value) || null);
    };

    return <Modal show={show} onHide={onClose}>
        <Modal.Header closeButton>
            <Modal.Title>Select audit</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Row>
                <Col xs={6}>
                    <Select isMulti={false} onChange={onOptionSelect} options={options}/>
                </Col>
                <Col xs={1} className="align-middle">OR</Col>
                <Col>
                    <Button variant='info' onClick={() => onSelect(null)}>Create new audit</Button>
                </Col>
            </Row>
        </Modal.Body>
    </Modal>
};

export default CreateReportDialog;
