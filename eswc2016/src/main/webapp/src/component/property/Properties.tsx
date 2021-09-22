import React, {useEffect, useState} from "react";
import {useDispatch} from "react-redux";
import {Properties as PropertiesType} from "../../model/Types";
import {ThunkDispatch} from "../../util/Util";
import {loadProperties} from "../../action/AsyncActions";
import {Button, Card, Table} from "react-bootstrap";
import PropertyEditRow from "./PropertyEditRow";
import PropertyRow from "./PropertyRow";

interface PropertiesProps {
    properties: PropertiesType;
    onChange: (v: PropertiesType) => void;
}

export interface PropertyRecord {
    property?: string;
    value?: string;
}

function flattenProperties(properties: PropertiesType) {
    const rows: PropertyRecord[] = [];
    for (let property in properties) {
        const values = properties[property];
        for (let i = 0, len = values.length; i < len; i++) {
            rows.push({property: property, value: values[i]});
        }
    }
    return rows;
}

function consolidateProperties(rows: PropertyRecord[]): PropertiesType {
    const result: PropertiesType = {};
    rows.forEach(r => {
        if (!result[r.property!]) {
            result[r.property!] = [];
        }
        (result[r.property!] as string[]).push(r.value!);
    });
    return result;
}

const Properties:React.FC<PropertiesProps> = props => {
    const dispatch: ThunkDispatch = useDispatch();
    useEffect(() => {
        dispatch(loadProperties());
    }, [dispatch]);
    const [editedIndex, setEditedIndex] = useState<number | null>(null);
    const properties = flattenProperties(props.properties);
    const onSave = (p: PropertyRecord) => {
        const toSave = properties.slice();
        properties[editedIndex!] = p;
        setEditedIndex(null);
        props.onChange(consolidateProperties(toSave));
    };
    const onRemove = (p: PropertyRecord) => {
        const toSave = properties.splice(properties.indexOf(p), 1);
        props.onChange(consolidateProperties(toSave));
    };
    const onAdd = () => {
        setEditedIndex(-1);
    }

    return (
        <Card>
            <Card.Header>Additional properties</Card.Header>
            <Card.Body>
            <Table striped={true} hover={true}>
                <thead>
                <tr>
                    <td className='col-xs-4 centered'>Property</td>
                    <td className='col-xs-5 centered'>Value</td>
                    <td className='col-xs-3 centered'>Actions</td>
                </tr>
                </thead>
                <tbody>
                {properties.map((p, index) => {
                    if (index === editedIndex) {
                        return <PropertyEditRow record={p} onSave={onSave} onCancel={() => setEditedIndex(null)}/>
                    } else {
                        return <PropertyRow record={p} onEdit={() => setEditedIndex(index)} onRemove={onRemove}/>
                    }
                })}
                {editedIndex && editedIndex === -1 && <PropertyEditRow record={{}} onSave={onSave} onCancel={() => setEditedIndex(null)}/>}
                </tbody>
            </Table>
            <Button variant='info' onClick={onAdd}>Add</Button>
            </Card.Body>
        </Card>
    );
};

export default Properties;
