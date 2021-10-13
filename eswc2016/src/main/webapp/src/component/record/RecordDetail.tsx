import React, {useState} from "react";
import Record, {Answer, Question} from "../../model/Record";
import {Button, ButtonToolbar, Col, Form, Modal, Row} from "react-bootstrap";
import QuestionSelector from "./QuestionSelector";
import CreateQuestion from "./CreateQuestion";
import {useDispatch} from "react-redux";
import {ThunkDispatch} from "../../util/Util";
import {createQuestion} from "../../action/AsyncActions";
import {GoPlus} from "react-icons/all";
import AnswerForm from "./Answer";
import RecordClassification from "./RecordClassification";

interface RecordDetailProps {
    record: Record;
    show: boolean;
    onClose: () => void;
    onSave: (r: Record) => void;
    onRemove: (r: Record) => void;
}

function isValid(question: Question | null, answer: Answer | null) {
    return question !== null && answer !== null && answer.has_data_value !== undefined && answer.has_data_value.trim().length !== 0;
}

const RecordDetail: React.FC<RecordDetailProps> = props => {
    const {record, show, onClose, onRemove, onSave} = props;
    const [question, setQuestion] = useState<Question | null>(record.has_question || null);
    const [answer, setAnswer] = useState<Answer>(record.has_answer || {});
    const [classification, setClassification] = useState<string[] | undefined>(record.types);
    const [showCreateQuestion, setShowCreateQuestion] = useState(false);
    const dispatch: ThunkDispatch = useDispatch();
    const onCreateQuestion = (q: Question) => {
        dispatch(createQuestion(q));
        setShowCreateQuestion(false);
    };
    const onSaveClick = () => {
        const newRecord = Object.assign({}, record, {
            has_question: question,
            has_answer: answer,
            types: classification
        });
        onSave(newRecord);
    };


    return <Modal show={show} onHide={onClose}>
        <CreateQuestion show={showCreateQuestion} onClose={() => setShowCreateQuestion(false)}
                        onCreate={onCreateQuestion}/>
        <Modal.Header closeButton>
            <Modal.Title>Create record</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <Row className="mb-3">
                <Col xs={10}>
                    <Form.Group>
                        <Form.Label>Question</Form.Label>
                        <QuestionSelector question={question} onSelect={setQuestion}/>
                    </Form.Group>
                </Col>
                <Col className="align-self-end">
                    <Button variant="primary" className="align-bottom" onClick={() => setShowCreateQuestion(true)}>
                        <GoPlus/>
                    </Button>
                </Col>
            </Row>
            <AnswerForm show={question !== null} answer={answer} onChange={setAnswer}/>
            <RecordClassification onChange={setClassification} show={question !== null} types={classification}/>
        </Modal.Body>
        <Modal.Footer>
            <ButtonToolbar className="float-end">
                <Button variant='success' className="me-2" onClick={onSaveClick} disabled={!isValid(question, answer)}>Save</Button>
                <Button variant="outline-primary" onClick={onClose}>Cancel</Button>
                {!record.isNew && <Button variant='warning' onClick={() => onRemove(record)}>Remove</Button>}
            </ButtonToolbar>
        </Modal.Footer>
    </Modal>
};

export default RecordDetail;
