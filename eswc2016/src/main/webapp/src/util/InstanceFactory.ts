import Event from "../model/Event";
import Report from "../model/Report";

export function createAudit(): Event {
    return {
        isNew: true,
        date: (Date.now() / 1000) * 1000
    };
}

export function createReport(audit?: object): Report {
    const report: any = {isNew: true, has_documentation_part: []};
    if (audit) {
        report.documents = audit;
    } else {
        report.documents = createAudit();
    }
    return report;
}
