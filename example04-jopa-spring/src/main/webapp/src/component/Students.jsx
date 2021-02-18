import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {createStudent, deleteStudent, loadStudents} from "../action/Actions";
import {Card, Col, Row, Table} from "react-bootstrap";
import StudentRow from "./StudentRow";
import CreateStudent from "./CreateStudent";

const Students = () => {
    const students = useSelector(state => state.students);
    const dispatch = useDispatch();
    React.useEffect(() => {
        dispatch(loadStudents());
    }, [dispatch]);
    const onCreate = student => {
        dispatch(createStudent(student));
    };
    const onDelete = student => {
        dispatch(deleteStudent(student));
    }

    return <Card variant="info">
        <Card.Header><h3>Students</h3></Card.Header>
        <Card.Body>
            <Row>
                <Col>
                    <Table striped hover bordered responsive size="sm">
                        <thead>
                        <tr>
                            <td>Name</td>
                            <td>Email</td>
                            <td className="text-center">Actions</td>
                        </tr>
                        </thead>
                        <tbody>
                        {students.map(s => <StudentRow key={s.key} onDelete={onDelete} student={s}/>)}
                        </tbody>
                    </Table>
                </Col>
            </Row>
            <Row>
                <Col>
                    <CreateStudent onSubmit={onCreate}/>
                </Col>
            </Row>
        </Card.Body>
    </Card>;
};

export default Students;