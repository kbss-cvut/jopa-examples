import React from "react";
import Record from "../../model/Record";
import Util from "../../util/Util";
import {Button} from "react-bootstrap";
import classNames from "classnames";

interface RecordRowProps {
    record: Record;
    onEdit: (r: Record) => void;
    onRemove: (r: Record) => void;
}

const RecordRow: React.FC<RecordRowProps> = props => {
    const {record, onEdit, onRemove} = props;

    const classification = record.types ? record.types[0] : '';
    const className = 'record-classification ' + Util.getClassificationClassName(classification);
    return <tr>
        <td className={classNames(className, "align-middle")}>{record!.has_question!.has_data_value}</td>
        <td className={classNames(className, "align-middle")}>{record!.has_answer!.has_data_value}</td>
        <td className={classNames(className, "align-middle actions")}>
            <Button variant='info' onClick={() => onEdit(record)}>Edit</Button>
            <Button variant='warning' onClick={() => onRemove(record)}>Remove</Button>
        </td>
    </tr>;
};

export default RecordRow;
