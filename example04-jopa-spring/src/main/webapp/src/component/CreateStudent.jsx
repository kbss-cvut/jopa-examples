import React from "react";
import PropTypes from "prop-types";
import {Button, ButtonToolbar, Card, Form} from "react-bootstrap";

const CreateStudent = ({onSubmit}) => {
    const [student, setStudent] = React.useState({
        firstName: "",
        lastName: "",
        email: ""
    });
    const onChange = e => {
        const change = {};
        change[e.target.name] = e.target.value;
        setStudent(Object.assign({}, student, change));
    };
    const onSave = () => {
        onSubmit(student);
    };
    const onKeyPress = e => {
        if (e.charCode === 13) {
            onSave();
        }
    }

    return <Card>
        <Card.Header><h5>Add Student</h5></Card.Header>
        <Card.Body>
            <Form.Group className="mb-3">
                <Form.Label>First name</Form.Label>
                <Form.Control name="firstName" value={student.firstName} onChange={onChange} size="sm"/>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Last name</Form.Label>
                <Form.Control name="lastName" value={student.lastName} onChange={onChange} size="sm"/>
            </Form.Group>
            <Form.Group className="mb-3">
                <Form.Label>Email</Form.Label>
                <Form.Control name="email" value={student.email} onChange={onChange} onKeyPress={onKeyPress} size="sm"/>
            </Form.Group>
            <ButtonToolbar className="mt-2 d-flex d-grid justify-content-end">
                <Button variant="success" onClick={onSave}>Submit</Button>
            </ButtonToolbar>
        </Card.Body>
    </Card>;
};

CreateStudent.propTypes = {
    onSubmit: PropTypes.func.isRequired
};

export default CreateStudent;