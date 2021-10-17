import {ReportItem} from "./Report";
import Event from "./Event";
import {Question} from "./Record";
import {Message} from "./Types";

export default class AppModel {
    public settings: { [key: string]: string };
    public reports: ReportItem[];
    public audits: Event[];
    public properties: string[];
    public questions: Question[];
    public messages: Message[];

    constructor() {
        this.settings = {};
        this.reports = [];
        this.audits = [];
        this.properties = [];
        this.questions = [];
        this.messages = [];
    }
}
