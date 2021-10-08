import {BaseEntity, Properties} from "./Types";
import {ReportItem} from "./Report";

export default interface Event extends BaseEntity {
    identifier?: number;
    title?: string;
    date: number;
    types?: string[];
    properties?: Properties;
    isDocumentedBy?: ReportItem[];

    isNew?: boolean;
}
