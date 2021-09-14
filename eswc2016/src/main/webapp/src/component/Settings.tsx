import React, {useState} from "react";
import {Card, Col, Form, Row} from "react-bootstrap";
import Constants from "../../js/constants/Constants";
import Input from "./Input";
import {useDispatch} from "react-redux";
import {loadSettings} from "../action/AsyncActions";

const Settings = () => {
    const [repoType, setRepoType] = useState<string>("sesame");
    const dispatch = useDispatch();
    React.useEffect(() => {
        dispatch(loadSettings(repoType));
    }, [repoType, dispatch]);

    return <div>
            <Card>
                <Card.Header>Settings</Card.Header>
                <Row>
                    <Col>
                        <label className='control-label'>Repository type</label>
                    </Col>
                </Row>
                <Row>
                    <Col xs={2}>
                        <Form.Check type='radio' value='sesame'
                               checked={repoType === 'sesame'} label='Sesame'
                               onChange={() => setRepoType("sesame")}/>
                    </Col>
                    <Col xs={2}>
                        <Form.Check type='radio' value='owlapi'
                               checked={repoType === 'owlapi'} label='OWL API'
                               onChange={() => setRepoType("owlapi")}/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Input type='textarea' value={Constants.STORAGE_INFO[repoType]} disabled/>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <p>
                        More information can be found at <a
                        href='https://github.com/kbss-cvut/jopa-examples/tree/master/eswc2016'>Github</a>.
                        </p>
                    </Col>
                </Row>
            </Card>
        </div>;
};

export default Settings;
