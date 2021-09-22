import {BaseEntity} from "./Types";
import Person from "./Person";

export interface Report extends BaseEntity {
    identifier?: number;
    auditTitle?: string;
    auditDate?: number;
    recordCount?: number;
    hasAuthor?: Person;
    created?: number;
}
