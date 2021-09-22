import Event from "./Event";
import {Properties} from "./Types";

export interface AuditFinding {
    id: string;
    severity: number;
    description?: string;
    properties?: Properties;
}

export default interface Audit extends Event {
    findings?: AuditFinding[];
}
