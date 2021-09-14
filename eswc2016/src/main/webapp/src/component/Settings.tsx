import React, {useState} from "react";
import {Card, Col, Form, Row} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {loadSettings, saveSettings} from "../action/AsyncActions";
import Constants from "../util/Constants";
import {ThunkDispatch} from "../util/Util";

const Settings = () => {
    const [repoType, setRepoType] = useState<string>("sesame");
    const dispatch: ThunkDispatch = useDispatch();
    React.useEffect(() => {
        dispatch(loadSettings(Constants.REPOSITORY_TYPE_PARAM)).then((type: string) => setRepoType(type));
    }, [repoType, dispatch]);
    const onChange = (value: string) => {
        setRepoType(value);
        const change = {};
        change[Constants.REPOSITORY_TYPE_PARAM] = value;
        dispatch(saveSettings(change));
    }

    return <div>
        <Card>
            <Card.Header>Settings</Card.Header>
            <Card.Body>
                <Row>
                    <Col>
                        <Form.Label className='control-label'>Repository type</Form.Label>
                    </Col>
                </Row>
                <Row className="mb-3">
                    <Col xs={2}>
                        <Form.Check type='radio' value='sesame'
                                    checked={repoType === 'sesame'} label='Sesame'
                                    onChange={() => onChange("sesame")}/>
                    </Col>
                    <Col xs={2}>
                        <Form.Check type='radio' value='owlapi'
                                    checked={repoType === 'owlapi'} label='OWL API'
                                    onChange={() => onChange("owlapi")}/>
                    </Col>
                </Row>
                <Row className="mb-3">
                    <Col>
                        <Form.Control as='textarea' rows={10} value={Constants.STORAGE_INFO[repoType]} disabled/>
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
            </Card.Body>
        </Card>
    </div>;
};

export default Settings;
