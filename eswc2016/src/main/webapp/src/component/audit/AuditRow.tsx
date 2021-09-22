import Event from "../../model/Event";
import React from "react";
import {Button} from "react-bootstrap";
import Util from "../../util/Util";
import {Link} from "react-router-dom";
import {Routing} from "../../util/Routing";

interface AuditRowProps {
    audit: Event;
    onRemove: (audit: Event) => void;
}

const AuditRow: React.FC<AuditRowProps> = props => {
    const {audit, onRemove} = props;

    return <tr>
        <td className="align-middle">{audit.title}</td>
        <td className='text-center align-middle'>{Util.formatDate(new Date(audit.date))}</td>
        <td className='actions'>
            <Link
                to={Routing.buildUrl("/audits/:auditId", {params: new Map<string, string>([["auditId", audit.identifier!.toString()]])})}>
                <Button variant='info' size='sm'>Edit</Button>
            </Link>
            <Button variant='warning' size='sm' onClick={() => onRemove(audit)}>Remove</Button>
        </td>
    </tr>;
};

export default AuditRow;
