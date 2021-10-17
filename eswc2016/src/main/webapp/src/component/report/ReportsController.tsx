import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {ThunkDispatch} from "../../util/Util";
import {loadReports, removeReport} from "../../action/AsyncActions";
import {ReportItem} from "../../model/Report";
import AppModel from "../../model/AppModel";
import Reports from "./Reports";
import CreateReportDialog from "./CreateReportDialog";
import Event from "../../model/Event";
import Routing from "../../util/Routing";
import {publishMessage} from "../../action/SyncActions";
import {trackPromise} from "react-promise-tracker";

const ReportsController: React.FC = () => {
    const dispatch: ThunkDispatch = useDispatch();
    useEffect(() => {
        trackPromise(dispatch(loadReports()), "main-view");
    }, [dispatch]);
    const [showCreateDialog, setShowCreateDialog] = useState(false);
    const reports = useSelector((state: AppModel) => state.reports);
    const onRemove = (report: ReportItem) => {
        trackPromise(dispatch(removeReport(report.identifier!)), "main-view").then(() => dispatch(publishMessage({
            message: "Report removed.",
            type: "success"
        })));
    };
    const onSelectAudit = (audit: Event | null) => {
        setShowCreateDialog(false);
        if (audit) {
            Routing.transitionTo('reports/create', {query: new Map<string, string>([["auditKey", audit.identifier!.toString()]])});
        } else {
            Routing.transitionTo('audits/create');
        }
    };


    return <>
        <CreateReportDialog show={showCreateDialog} onClose={() => setShowCreateDialog(false)}
                            onSelect={onSelectAudit}/>
        <Reports reports={reports} createReport={() => setShowCreateDialog(true)} removeReport={onRemove}/>
    </>;
};

export default ReportsController;
