import {PropertyRecord} from "./Properties";
import {Button} from "react-bootstrap";
import React from "react";

interface PropertyRowProps {
    record: PropertyRecord;
    onEdit: (record: PropertyRecord) => void;
    onRemove: (record: PropertyRecord) => void;
}

const PropertyRow: React.FC<PropertyRowProps> = props => {
    const {record, onEdit, onRemove} = props;

    return <tr>
        <td className='properties align-middle'>{record.property}</td>
        <td className='properties align-middle'>{record.value}</td>
        <td className='actions'>
            <Button variant='info' size='sm' onClick={() => onEdit(record)}>Edit</Button>
            <Button variant='warning' size='sm' onClick={() => onRemove(record)}>Remove</Button>
        </td>
    </tr>;
};

export default PropertyRow;
