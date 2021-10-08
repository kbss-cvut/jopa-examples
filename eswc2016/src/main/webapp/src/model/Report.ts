import {BaseEntity} from "./Types";
import Person from "./Person";
import Record from "./Record";

export interface Report extends BaseEntity {
    identifier?: number;
    auditTitle?: string;
    auditDate?: number;
    recordCount?: number;
    hasAuthor?: Person;
    created?: number;
    has_documentation_part?: Record[];
}
