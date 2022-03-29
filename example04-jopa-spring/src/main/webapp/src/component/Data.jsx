import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {loadData, selectDataFormat} from "../action/Actions";
import {Card, Form} from "react-bootstrap";

export const formats = [
    {value: "rdfxml", label: "RDF/XML (Pretty)"},
    {value: "json", label: "JSON"},
    {value: "turtle", label: "Turtle"}
];

const Data = () => {
    const dispatch = useDispatch();
    const format = useSelector(state => state.dataFormat);
    const data = useSelector(state => state.data);
    React.useEffect(() => {
        dispatch(loadData());
    }, [dispatch]);
    const onSelectFormat = e => {
        dispatch(selectDataFormat(e.currentTarget.value));
        dispatch(loadData());
    }

    return <Card style={{height: '100%'}}>
        <Card.Header><h3>Repository Content</h3></Card.Header>
        <Card.Body>
            <Form.Group>
                <Form.Control as="select" onChange={onSelectFormat} size="sm" value={format}>
                    {formats.map(f => <option key={f.value} value={f.value}>{f.label}</option>)}
                </Form.Control>
            </Form.Group>
            <Form.Group>
                <Form.Control as="textarea" rows={20} value={format === "json" ? JSON.stringify(data, null, 2) : data} size="sm" readOnly={true}/>
            </Form.Group>
        </Card.Body>
    </Card>
};

export default Data;