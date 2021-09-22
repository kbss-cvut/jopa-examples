import {BaseEntity, Properties} from "./Types";
import {Report} from "./Report";

export default interface Event extends BaseEntity {
    identifier?: number;
    title?: string;
    date: number;
    types?: string[];
    properties?: Properties;
    isDocumentedBy?: Report[];

    isNew?: boolean;
}
