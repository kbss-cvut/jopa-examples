import {useParams} from "react-router";
import AuditDetail from "./AuditDetail";
import Routing from "../../util/Routing";
import {useEffect, useState} from "react";
import {ThunkDispatch} from "../../util/Util";
import {useDispatch} from "react-redux";
import {createAudit as createAuditAction, loadAudit, updateAudit} from "../../action/AsyncActions";
import Event from "../../model/Event";
import {createAudit} from "../../util/InstanceFactory";

const AuditController = () => {
    const {auditId} = useParams<{ auditId: string }>();
    const dispatch: ThunkDispatch = useDispatch();
    const [audit, setAudit] = useState<Event | null>(null);
    useEffect(() => {
        if (auditId) {
            dispatch(loadAudit(Number(auditId))).then(audit => setAudit(audit));
        } else {
            setAudit(createAudit())
        }
    }, [auditId, dispatch, setAudit]);
    const onAddReport = (auditId: number) => {
        Routing.transitionTo("/reports/create", {query: new Map<string, string>([["auditId", auditId.toString()]])});
    };
    const onCancel = () => {
        Routing.transitionTo("/audits");
    };
    const onChange = (change: Partial<Event>) => {
        setAudit(Object.assign({}, audit, change));
    };
    const onSave = () => {
        if (audit!.isNew) {
            dispatch(createAuditAction(audit!));
        } else {
            dispatch(updateAudit(audit!));
        }
    };

    return audit &&
        <AuditDetail audit={audit} addReport={onAddReport} onChange={onChange} onSave={onSave} onCancel={onCancel}/>;
};

export default AuditController;
