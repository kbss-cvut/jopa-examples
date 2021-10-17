import {useParams} from "react-router";
import AuditDetail from "./AuditDetail";
import Routing from "../../util/Routing";
import {useEffect, useState} from "react";
import {ThunkDispatch} from "../../util/Util";
import {useDispatch} from "react-redux";
import {createAudit as createAuditAction, loadAudit, updateAudit} from "../../action/AsyncActions";
import Event from "../../model/Event";
import {createAudit} from "../../util/InstanceFactory";
import {publishMessage} from "../../action/SyncActions";

const AuditController = () => {
    const {auditKey} = useParams<{ auditKey: string }>();
    const dispatch: ThunkDispatch = useDispatch();
    const [audit, setAudit] = useState<Event | null>(null);
    useEffect(() => {
        if (auditKey) {
            dispatch(loadAudit(Number(auditKey))).then(audit => setAudit(audit));
        } else {
            setAudit(createAudit())
        }
    }, [auditKey, dispatch, setAudit]);
    const onAddReport = (auditId: number) => {
        Routing.transitionTo("/reports/create", {query: new Map<string, string>([["auditKey", auditId.toString()]])});
    };
    const onCancel = () => {
        Routing.transitionTo("/audits");
    };
    const onChange = (change: Partial<Event>) => {
        setAudit(Object.assign({}, audit, change));
    };
    const onSave = () => {
        if (audit!.isNew) {
            dispatch(createAuditAction(audit!)).then(() => {
                dispatch(publishMessage({message: "Audit created.", type: "success"}));
                Routing.transitionTo("/audits");
            });
        } else {
            dispatch(updateAudit(audit!)).then(() => dispatch(publishMessage({
                message: "Audit saved.",
                type: "success"
            })));
        }
    };

    return audit &&
        <AuditDetail audit={audit} addReport={onAddReport} onChange={onChange} onSave={onSave} onCancel={onCancel}/>;
};

export default AuditController;
