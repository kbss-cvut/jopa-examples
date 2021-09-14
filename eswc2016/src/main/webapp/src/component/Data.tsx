import {useEffect, useState} from "react";
import {Card, Form} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {loadData} from "../action/AsyncActions";
import {ThunkDispatch} from "../util/Util";

const Data = () => {
    const [data, setData] = useState("");
    const [format, setFormat] = useState("rdfxml");
    const dispatch: ThunkDispatch = useDispatch();
    useEffect(() => {
        dispatch(loadData(format)).then((content: string) => setData(content));
    }, [dispatch, format, setData]);


    return <Card style={{height: '100%'}}>
        <Card.Header>Repository Content</Card.Header>
        <Form.Group>
            <Form.Control as="select" onChange={e => setFormat(e.currentTarget.value)} value={format}>
                <option value='rdfxml'>RDF/XML (Pretty)</option>
                <option value='json'>JSON</option>
                <option value='turtle'>Turtle</option>
            </Form.Control>
        </Form.Group>
        <Form.Group>
            <Form.Control as="textarea" rows={20} value={data} disabled={true}/>
        </Form.Group>
    </Card>;
};

export default Data;
