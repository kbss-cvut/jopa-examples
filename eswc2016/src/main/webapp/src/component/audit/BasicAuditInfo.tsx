import React from "react";
import {Col, Form, Row} from "react-bootstrap";
import Datetime from "react-datetime";
import Event from "../../model/Event";

interface BasicAuditInfoProps {
    audit: Event;
    onChange: (change: Partial<Event>) => void;
    disabled?: boolean;
}

const BasicAuditInfo: React.FC<BasicAuditInfoProps> = props => {
    const {audit, disabled, onChange} = props;

    return <>
        <Row className="mb-3">
            <Col xs={6}>
                <Form.Group>
                    <Form.Label>Title</Form.Label>
                    <Form.Control type="text" value={audit.title} disabled={disabled}
                                  onChange={e => onChange({title: e.currentTarget.value})}/>
                </Form.Group>
            </Col>
        </Row>
        <Row className='mb-3'>
            <Col>
                <Form.Group>
                    <Form.Label>Audit date</Form.Label>
                    <Datetime value={new Date(audit.date)} onChange={v => onChange({date: v})}
                              inputProps={{disabled}}/>
                </Form.Group>
            </Col>
        </Row>
    </>;
};

export default BasicAuditInfo;
