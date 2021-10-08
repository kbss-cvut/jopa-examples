import {Button, ButtonToolbar, Card, Col, Row} from "react-bootstrap";
import React from "react";
import BasicAuditInfo from "./BasicAuditInfo";
import Event from "../../model/Event";
import Properties from "../property/Properties";
import AuditReports from "./AuditReports";
import {ReportItem} from "../../model/Report";
import Util, {ThunkDispatch} from "../../util/Util";
import {useDispatch} from "react-redux";
import {loadAudit, removeReport} from "../../action/AsyncActions";


interface AuditDetailProps {
    audit: Event;
    addReport: (auditId: number) => void;
    onChange: (change: Partial<Event>) => void;
    onSave: () => void;
    onCancel: () => void;
}

const AuditDetail: React.FC<AuditDetailProps> = props => {
    const {audit, addReport, onChange, onSave, onCancel} = props;
    const dispatch: ThunkDispatch = useDispatch();
    const onRemove = (r: ReportItem) => {
        dispatch(removeReport(r.identifier!)).then(() => dispatch(loadAudit(audit.identifier!)));
    };

    return <>
        <Card>
            <Card.Header>Audit</Card.Header>
            <Card.Body>
                <BasicAuditInfo audit={audit} onChange={onChange}/>

                {!audit.isNew && <AuditReports addReport={() => addReport(audit.identifier!)} removeReport={onRemove}
                                               reports={Util.sanitizeArray(audit.isDocumentedBy)}/>}

                <Properties properties={audit.properties} onChange={p => onChange({properties: p})}/>

                <Row>
                    <Col>
                        <ButtonToolbar>
                            <Button variant='primary' onClick={onSave}>{audit.isNew ? 'Create' : 'Save'}</Button>
                            <Button onClick={onCancel}>Cancel</Button>
                        </ButtonToolbar>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    </>
};

export default AuditDetail;
