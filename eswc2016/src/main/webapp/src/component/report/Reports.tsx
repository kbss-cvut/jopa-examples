import React from "react";
import {ReportItem} from "../../model/Report";
import Util from "../../util/Util";
import {Button, Card, Col, Row, Table} from "react-bootstrap";
import ReportRow from "./ReportRow";

interface ReportsProps {
    reports: ReportItem[];
    createReport: () => void;
    removeReport: (report: ReportItem) => void;
}

const Reports: React.FC<ReportsProps> = props => {
    const {reports, createReport, removeReport} = props;

    let content: JSX.Element;
    if (Util.sanitizeArray(reports).length === 0) {
        content = <div className="mb-3 fst-italic">There are no reports, yet.</div>;
    } else {
        content = <Table striped bordered hover>
            <thead>
            <tr>
                <th className='col-xs-3 text-center'>Audit title</th>
                <th className='col-xs-3 text-center'>Audit date</th>
                <th className='col-xs-3 text-center'>Records</th>
                <th className='col-xs-3 text-center'>Actions</th>
            </tr>
            </thead>
            <tbody>
            {reports.map(r => <ReportRow key={r.id} report={r} onRemove={() => removeReport(r)}/>)}
            </tbody>
        </Table>;
    }

    return <Card>
        <Card.Header>Reports</Card.Header>
        <Card.Body>
            <Row>
                <Col>
                    {content}
                </Col>
            </Row>
            <Row>
                <Col>
                    <Button variant='info' onClick={createReport}>Create report</Button>
                </Col>
            </Row>
        </Card.Body>
    </Card>
};

export default Reports;
