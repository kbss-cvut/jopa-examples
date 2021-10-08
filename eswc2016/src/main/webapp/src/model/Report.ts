import {BaseEntity, Properties} from "./Types";
import Person from "./Person";
import Record from "./Record";
import Event from "./Event";

export interface ReportItem extends BaseEntity {
    identifier?: number;
    auditTitle?: string;
    auditDate?: number;
    recordCount?: number;
    hasAuthor?: Person;
    created?: number;
}

export default interface Report extends BaseEntity {
    identifier?: number;
    documents: Event;
    hasAuthor?: Person;
    created?: number;
    has_documentation_part?: Record[];
    properties?: Properties;

    isNew?: boolean;
}
