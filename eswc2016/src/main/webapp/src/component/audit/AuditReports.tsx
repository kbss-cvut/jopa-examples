import {Report} from "../../model/Report";
import React, {useState} from "react";
import {Button, ButtonToolbar, Card, Col, Row, Table} from "react-bootstrap";
import DeleteDialog from "../DeleteDialog";
import Util from "../../util/Util";
import {Link} from "react-router-dom";
import {Routing} from "../../util/Routing";

interface AuditReportsProps {
    addReport: () => void;
    removeReport: (r: Report) => void;
    reports: Report[];
}

const AuditReports: React.FC<AuditReportsProps> = props => {
    const {addReport, removeReport, reports} = props;
    const [showDeleteDialog, setShowDeleteDialog] = useState(false);
    const [toRemove, setToRemove] = useState<Report | null>(null);
    const onRemove = (r: Report) => {
        setToRemove(r);
        setShowDeleteDialog(true);
    };
    const onRemoveSubmit = () => {
        removeReport(toRemove!);
        setToRemove(null);
        setShowDeleteDialog(false);
    };

    return <>
        <DeleteDialog show={showDeleteDialog} onSubmit={onRemoveSubmit} onClose={() => setShowDeleteDialog(false)}/>
        <Card>
            <Card.Header>Reports</Card.Header>
            <Card.Body>
                <Row className='mb-3'>
                    <Col>
                        {reports.length > 0 ? <Table striped bordered hover>
                            <thead>
                            <tr>
                                <td className='col-xs-3 text-center'>Date created</td>
                                <td className='col-xs-3 text-center'>Author</td>
                                <td className='col-xs-3 text-center'>Records</td>
                                <td className='col-xs-3 text-center'>Actions</td>
                            </tr>
                            </thead>
                            <tbody>
                            {reports.map(r => <ReportRow key={r.id} report={r} onRemove={onRemove}/>)}
                            </tbody>
                        </Table> : <p className="italics">There are no reports here.</p>}
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <ButtonToolbar>
                            <Button variant='info' onClick={addReport}>Add Report</Button>
                        </ButtonToolbar>
                    </Col>
                </Row>
            </Card.Body>
        </Card>
    </>
};

export default AuditReports;

interface ReportRowProps {
    report: Report;
    onRemove: (r: Report) => void;
}

const ReportRow: React.FC<ReportRowProps> = props => {
    const {report, onRemove} = props;

    return <tr>
        <td className="align-middle">{Util.formatDate(new Date(report.created!))}</td>
        <td className='text-center align-middle'>{report.hasAuthor ? `${report.hasAuthor.firstName} ${report.hasAuthor.lastName}` : ''}</td>
        <td className="text-right align-middle">{report.recordCount ? report.recordCount : '?'}</td>
        <td className='actions'>
            <Button variant='warning' size='sm' onClick={() => onRemove(report)}>Remove</Button>
            <Link
                to={Routing.buildUrl("/reports/:identifier", {params: new Map<string, string>([["identifier", report.identifier!.toString()]])})}>
                <Button variant='info' size='sm'>Edit</Button>
            </Link>
        </td>
    </tr>;
};
