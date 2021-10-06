import {Report} from "./Report";
import Event from "./Event";
import {Question} from "./Record";

export default class AppModel {
    public settings: { [key: string]: string };
    public reports: Report[];
    public audits: Event[];
    public properties: string[];
    public questions: Question[];

    constructor() {
        this.settings = {};
        this.reports = [];
        this.audits = [];
        this.properties = [];
        this.questions = [];
    }
}
