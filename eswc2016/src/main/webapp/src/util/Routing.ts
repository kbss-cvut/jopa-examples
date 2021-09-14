import {createHashHistory, History} from 'history';

class Routing {

    private readonly mHistory: History;

    constructor() {
        this.mHistory = createHashHistory();
    }

    get history(): History {
        return this.mHistory;
    }

    transitionTo(path: string) {
        this.history.push(path);
    }
}

const INSTANCE = new Routing();

export default INSTANCE;
