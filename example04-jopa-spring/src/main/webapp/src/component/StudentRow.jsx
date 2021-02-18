import React from "react";
import {Button} from "react-bootstrap";
import PropTypes from "prop-types";

const StudentRow = ({student, onDelete}) => {
    const name = student.firstName + " " + student.lastName;
    return <tr>
        <td className="align-middle">{name}</td>
        <td className="align-middle">{student.email}</td>
        <td className="text-center align-middle"><Button size='sm' variant='warning' onClick={() => onDelete(student)}>Delete</Button>
        </td>
    </tr>;
};

StudentRow.propTypes = {
    student: PropTypes.object.isRequired,
    onDelete: PropTypes.func.isRequired
};

export default StudentRow;