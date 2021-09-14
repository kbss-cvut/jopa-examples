export function createAudit() {
    return {
        isNew: true,
        date: (Date.now() / 1000) * 1000
    };
}

export function createReport(audit?: object) {
    var report: any = {isNew: true, has_documentation_part: []};
    if (audit) {
        report.documents = audit;
    } else {
        report.documents = createAudit();
    }
    return report;
}
