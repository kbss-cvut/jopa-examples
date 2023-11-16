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

import {createHashHistory, History} from 'history';

export class Routing {

    private readonly mHistory: History;

    constructor() {
        this.mHistory = createHashHistory();
    }

    private static setPathParams(path: string, params: Map<string, string>) {
        for (const pair of Array.from(params.entries())) {
            path = path.replace(":" + pair[0], pair[1]);
        }
        return path;
    }

    private static setQueryParams(path: string, params: Map<string, string>) {
        const paramValuePairs = Array.from(params.entries()).map(
            (pair) => pair[0] + "=" + pair[1]
        );
        return paramValuePairs.length > 0
            ? path + "?" + paramValuePairs.join("&")
            : path;
    }

    public static buildUrl(path: string, options: {
        params?: Map<string, string>;
        query?: Map<string, string>;
    } = {}) {
        if (options.params) {
            path = Routing.setPathParams(path, options.params);
        }
        if (options.query) {
            path = Routing.setQueryParams(path, options.query);
        }
        return path;
    }

    get history(): History {
        return this.mHistory;
    }

    transitionTo(path: string, options: {
        params?: Map<string, string>;
        query?: Map<string, string>;
    } = {}) {
        this.history.push(Routing.buildUrl(path, options));
    }
}

const INSTANCE = new Routing();

export default INSTANCE;
