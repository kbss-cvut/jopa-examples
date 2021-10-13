import React, {useEffect, useState} from "react";
import Report from "../../model/Report";
import {useDispatch} from "react-redux";
import Util, {ThunkDispatch} from "../../util/Util";
import {useLocation, useParams} from "react-router";
import {loadAudit, loadReport, saveReport} from "../../action/AsyncActions";
import {createReport} from "../../util/InstanceFactory";
import {Button, ButtonToolbar, Card, Col, Row} from "react-bootstrap";
import BasicAuditInfo from "../audit/BasicAuditInfo";
import ReportRecords from "../record/ReportRecords";
import Properties from "../property/Properties";
import Routing from "../../util/Routing";

function empty() {
}

const ReportDetail: React.FC = () => {
    const {reportKey} = useParams<{ reportKey?: string }>();
    const location = useLocation();
    const [report, setReport] = useState<Report>();
    const dispatch = useDispatch<ThunkDispatch>();
    useEffect(() => {
        if (reportKey) {
            dispatch(loadReport(Number(reportKey))).then((r: Report) => setReport(r));
        } else {
            const auditKey = Util.extractQueryParam(location.search, "auditKey");
            if (auditKey) {
                dispatch(loadAudit(Number(auditKey))).then((a: Event) => setReport(createReport(a)));
            } else {
                setReport(createReport());
            }
        }
    }, [reportKey, dispatch, location, setReport]);
    const onChange = (change: Partial<Report>) => {
        setReport(Object.assign({}, report, change));
    };
    const onSave = () => {
        dispatch(saveReport(report!)).then((r: Report) => setReport(r));
    };
    if (!report) {
        return null;
    }

    return <Card id="report">
        <Card.Header>Report</Card.Header>
        <Card.Body>
            <Card className="mb-3">
                <Card.Header>Audit info</Card.Header>
                <Card.Body>
                    <BasicAuditInfo audit={report!.documents} onChange={empty} disabled={true}/>
                </Card.Body>
            </Card>

            {!report.isNew && <Row className='mb-3'>
                <Col className='italics'>
                    {`Created on ${Util.formatDate(new Date(report.created!))} by ${report.hasAuthor!.firstName} ${report.hasAuthor!.lastName}.`}
                </Col>
            </Row>}

            <ReportRecords report={report!} onChange={onChange}/>

            <Properties properties={report!.properties} onChange={onChange}/>

            <ButtonToolbar className="float-end">
                <Button variant='success' className="me-2" onClick={onSave}>Save</Button>
                <Button variant="outline-primary" onClick={() => Routing.transitionTo('reports')}>Cancel</Button>
            </ButtonToolbar>
        </Card.Body>
    </Card>;
};

export default ReportDetail;
