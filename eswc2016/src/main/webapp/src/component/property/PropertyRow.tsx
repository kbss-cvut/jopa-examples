import {PropertyRecord} from "./Properties";
import {Button, ButtonToolbar} from "react-bootstrap";
import React from "react";

interface PropertyRowProps {
    record: PropertyRecord;
    onEdit: (record: PropertyRecord) => void;
    onRemove: (record: PropertyRecord) => void;
}

const PropertyRow: React.FC<PropertyRowProps> = props => {
    const {record, onEdit, onRemove} = props;

    return <tr>
        <td className='text-center align-middle'>{record.property}</td>
        <td className='text-center align-middle'>{record.value}</td>
        <td className='align-middle text-center'>
            <ButtonToolbar>
                <Button variant='info' size='sm' className="me-1" onClick={() => onEdit(record)}>Edit</Button>
                <Button variant='warning' size='sm' onClick={() => onRemove(record)}>Remove</Button>
            </ButtonToolbar>
        </td>
    </tr>;
};

export default PropertyRow;
