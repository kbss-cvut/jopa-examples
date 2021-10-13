import React, {useEffect, useState} from "react";
import {ThunkDispatch} from "../../util/Util";
import {useDispatch, useSelector} from "react-redux";
import {loadAudits, removeAudit} from "../../action/AsyncActions";
import AppModel from "../../model/AppModel";
import Event from "../../model/Event";
import {Card, Table} from "react-bootstrap";
import AuditRow from "./AuditRow";
import DeleteDialog from "../DeleteDialog";
import {Link} from "react-router-dom";

const Audits: React.FC = () => {
    const dispatch: ThunkDispatch = useDispatch();
    useEffect(() => {
        dispatch(loadAudits());
    }, [dispatch]);
    const audits = useSelector((state: AppModel) => state.audits);
    const [toRemove, setToRemove] = useState<Event | null>(null);
    const onSubmitRemove = () => {
        dispatch(removeAudit(toRemove!.identifier!));
        setToRemove(null);
    };

    return <Card>
        <Card.Header>Audits</Card.Header>
        <Table striped bordered hover>
            <thead>
            <tr>
                <td className="col-xs-7 centered">Title</td>
                <td className="col-xs-3 centered">Date</td>
                <td className="col-xs-2 centered">Actions</td>
            </tr>
            </thead>
            <tbody>
            {audits.map(a => <AuditRow audit={a} onRemove={(a: Event) => setToRemove(a)}/>)}
            </tbody>
        </Table>
        <Link to="/audits/create" className="btn btn-info">New Audit</Link>
        <DeleteDialog show={toRemove !== null} onSubmit={onSubmitRemove} onClose={() => setToRemove(null)}/>
    </Card>;
};

export default Audits;
