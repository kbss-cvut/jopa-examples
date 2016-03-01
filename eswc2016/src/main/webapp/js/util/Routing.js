'use strict';

import createHashHistory from 'history/lib/createHashHistory';
import RouterStore from '../stores/RouterStore';

class Routing {
    constructor() {
        this.history = createHashHistory();
    }

    transitionTo(path, payload) {
        if (payload) {
            RouterStore.setTransitionPayload(path, payload);
        } else {
            RouterStore.setTransitionPayload(path, null);
        }
        this.history.pushState(null, path);
    }

    getHistory() {
        return this.history;
    }
}

export default new Routing();
