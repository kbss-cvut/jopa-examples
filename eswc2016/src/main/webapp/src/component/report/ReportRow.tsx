import {Button} from "react-bootstrap";
import React from "react";
import Util from "../../util/Util";
import {ReportItem} from "../../model/Report";
import Routing from "../../util/Routing";

interface ReportRowProps {
    report: ReportItem;
    onRemove: (r: ReportItem) => void;
}

const ReportRow: React.FC<ReportRowProps> = props => {
    const {report, onRemove} = props;

    return <tr>
        <td style={{verticalAlign: 'middle'}}>{report.auditTitle}</td>
        <td className='text-center align-middle'>{Util.formatDate(new Date(report.auditDate!))}</td>
        <td className="text-right align-middle">{report.recordCount ? report.recordCount : '?'}</td>
        <td className='actions'>
            <Button variant="info" onClick={() => Routing.transitionTo("#/reports/" + report.identifier)}>Edit</Button>
            <Button variant='warning' onClick={() => onRemove(report)}>Remove</Button>
        </td>
    </tr>;
};

export default ReportRow;
