import React, {useState} from "react";
import {PropertyRecord} from "./Properties";
import {Button, ButtonToolbar, Form} from "react-bootstrap";
import Select from "react-select";
import {SelectOption} from "../../model/Types";
import {useSelector} from "react-redux";
import AppModel from "../../model/AppModel";
import Util from "../../util/Util";

interface PropertyEditRowProps {
    record: PropertyRecord;
    onSave: (record: PropertyRecord) => void;
    onCancel: () => void;
}

const PropertyEditRow: React.FC<PropertyEditRowProps> = props => {
    const {record, onSave, onCancel} = props;
    const [data, setData] = useState(Object.assign({}, record));
    const onOptionSelect = (opt: SelectOption | null) => {
        setData(Object.assign({}, data, {property: opt!.value}));
    };
    const properties = useSelector((state: AppModel) => state.properties);
    const options = Util.sanitizeArray(properties).map(p => ({value: p, label: p}));

    return <tr>
        <td className='align-middle'>
            <Select isMulti={false} onChange={onOptionSelect} options={options}
                    value={options.find(o => o.value === data.property)}/>
        </td>
        <td className='align-middle'>
            <div>
                <Form.Group>
                    <Form.Control type="text" value={data.value}
                                  onChange={e => setData(Object.assign({}, data, {value: e.currentTarget.value}))}/>
                </Form.Group>
            </div>
        </td>
        <td className='text-center align-middle'>
            <ButtonToolbar>
                <Button variant='primary' size='sm' className="me-1" onClick={() => onSave(data)}
                        disabled={!data.property || (data.value || "").trim().length === 0}>Save</Button>
                <Button variant="outline-primary" size='sm' onClick={onCancel}>Cancel</Button>
            </ButtonToolbar>
        </td>
    </tr>;
};

export default PropertyEditRow;
