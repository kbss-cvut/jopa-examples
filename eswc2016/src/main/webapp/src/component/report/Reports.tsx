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
        content = <div className="form-group italics">There are no reports, yet.</div>;
    } else {
        content = <Table striped={true} responsive={true}>
            <thead>
            <tr>
                <th className='col-xs-3 centered'>Audit title</th>
                <th className='col-xs-3 centered'>Audit date</th>
                <th className='col-xs-3 centered'>Records</th>
                <th className='col-xs-3 centered'>Actions</th>
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
