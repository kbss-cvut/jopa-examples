///
/// JOPA Examples
/// Copyright (C) 2023 Czech Technical University in Prague
///
/// This library is free software; you can redistribute it and/or
/// modify it under the terms of the GNU Lesser General Public
/// License as published by the Free Software Foundation; either
/// version 3.0 of the License, or (at your option) any later version.
///
/// This library is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
/// Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public
/// License along with this library.
///

import Event from "../model/Event";
import Report from "../model/Report";

export function createAudit(): Event {
    return {
        title: "",
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
