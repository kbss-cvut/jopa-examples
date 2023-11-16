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
