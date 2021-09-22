import React, {useState} from "react";
import {PropertyRecord} from "./Properties";
import {Button, Form} from "react-bootstrap";
import Select from "react-select";
import {SelectOption} from "../../model/Types";
import {useSelector} from "react-redux";
import AppModel from "../../model/AppModel";

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
    const options = properties.map(p => ({value: p, label: p}));

    return <tr>
        <td className='properties'>
            <Select isMulti={false} onChange={onOptionSelect} options={options}
                    value={options.find(o => o.value === data.property)}/>
        </td>
        <td className='properties'>
            <div>
                <Form.Group>
                    <Form.Control type="text" size="sm" value={data.value}
                                  onChange={e => setData(Object.assign({}, data, {value: e.currentTarget.value}))}/>
                </Form.Group>
            </div>
        </td>
        <td className='actions align-middle'>
            <Button variant='primary' size='sm' onClick={() => onSave(data)}
                    disabled={!data.property || data.value.trim().length === 0}>Save</Button>
            <Button variant="outline-primary" size='sm' onClick={onCancel}>Cancel</Button>
        </td>
    </tr>;
};

export default PropertyEditRow;
