import React, {useState} from "react";
import Report from "../../model/Report";
import Record from "../../model/Record";
import Util from "../../util/Util";
import {Button, Card, Col, Row, Table} from "react-bootstrap";
import RecordRow from "./RecordRow";
import RecordDetail from "./RecordDetail";

interface ReportRecordsProps {
    report: Report;
    onChange: (change: Partial<Report>) => void;
}

const ReportRecords: React.FC<ReportRecordsProps> = props => {
    const {report, onChange} = props;
    const records = Util.sanitizeArray(report.has_documentation_part);
    const [currentRecord, setCurrentRecord] = useState<Record | null>(null);
    const onRemove = (r: Record) => {
        const copy = [...records];
        copy.splice(copy.indexOf(r), 1);
        onChange({has_documentation_part: copy});
    };
    const onSave = () => {
        const copy = [...records];
        if (currentRecord!.isNew) {
            delete currentRecord!.isNew;
            copy.push(currentRecord!);
        } else {
            const index = records.findIndex(r => r.id === currentRecord!.id);
            copy.splice(index, 1, currentRecord!);
        }
        onChange({has_documentation_part: copy});
        setCurrentRecord(null);
    }
    const onAdd = () => {
        setCurrentRecord({isNew: true});
    };
    const hasRecords = currentRecord !== null || records.length > 0;

    return <Card>
        <Card.Header>Records</Card.Header>
        <Card.Body>
            {currentRecord &&
            <RecordDetail record={currentRecord} show={true} onClose={() => setCurrentRecord(null)} onSave={onSave}
                          onRemove={onRemove}/>}
            {hasRecords ? <Table striped bordered hover>
                <thead>
                <tr>
                    <th className='text-center'>Question</th>
                    <th className='text-center'>Answer</th>
                    <th className='text-center'>Actions</th>
                </tr>
                </thead>
                <tbody>
                {records.map(r => <RecordRow key={r.id} record={r} onEdit={setCurrentRecord} onRemove={onRemove}/>)}
                </tbody>
            </Table> : <div className='italics form-group'>There are no records, yet.</div>}
            <Row>
                <Col>
                    <Button variant='info' onClick={onAdd}>Add Record</Button>
                </Col>
            </Row>
        </Card.Body>
    </Card>;
};

export default ReportRecords;
