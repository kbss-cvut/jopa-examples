'use strict';

import createHashHistory from 'history/lib/createHashHistory'

class Routing {
    constructor() {
        this.history = createHashHistory();
    }

    transitionTo(path) {
        this.history.pushState(null, path)
    }

    getHistory() {
        return this.history;
    }
}

export default new Routing();
