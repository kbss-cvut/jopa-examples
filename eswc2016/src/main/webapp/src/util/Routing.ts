import {createHashHistory, History} from 'history';

class Routing {

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
