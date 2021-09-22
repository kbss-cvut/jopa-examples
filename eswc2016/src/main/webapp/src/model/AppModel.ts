import {Report} from "./Report";
import Audit from "./Audit";

export default class AppModel {
    public settings: { [key: string]: string };
    public reports: Report[];
    public audits: Audit[];
    public properties: string[];

    constructor() {
        this.settings = {};
        this.reports = [];
        this.audits = [];
        this.properties = [];
    }
}
