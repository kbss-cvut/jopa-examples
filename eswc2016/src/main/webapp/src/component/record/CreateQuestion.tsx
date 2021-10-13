import React from "react";
import {Button, ButtonToolbar, Form, Modal} from "react-bootstrap";
import {Question} from "../../model/Record";

interface CreateQuestionProps {
    show: boolean;
    onClose: () => void;
    onCreate: (q: Question) => void;
}

const CreateQuestion: React.FC<CreateQuestionProps> = props => {
    const {show, onClose, onCreate} = props;
    const [content, setContent] = React.useState("");
    React.useEffect(() => {
        setContent("");
    }, [show, setContent]);
    const onSaveClick = () => {
        onCreate({has_data_value: content});
    };

    return <Modal show={show} onHide={onClose}>
        <Modal.Header closeButton>
            <Modal.Title>Create question</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Form.Group>
                <Form.Label>Question</Form.Label>
                <Form.Control value={content} onChange={e => setContent(e.currentTarget.value)}/>
            </Form.Group>
        </Modal.Body>
        <Modal.Footer>
            <ButtonToolbar className="float-end">
                <Button variant="success" className="me-2" onClick={onSaveClick}>Create</Button>
                <Button variant="outline-primary" onClick={onClose}>Cancel</Button>
            </ButtonToolbar>
        </Modal.Footer>
    </Modal>;
};

export default CreateQuestion;
