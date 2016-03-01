'use strict';

/**
 * Manages passing payloads on routing transition.
 *
 * For example, when one wants to pass and object when transitioning to another route, this store will be used to store
 * the payload object and the target route handler can ask for it.
 */
module.exports = {

    transitionPayload: {},

    setTransitionPayload: function (path, payload) {
        if (!payload) {
            delete this.transitionPayload[path];
        } else {
            this.transitionPayload[path] = payload;
        }
    },

    /**
     * Gets the specified route's payload, if there is any.
     * @param path Route name
     * @return {*} Route transition payload or null if there is none for the specified path
     */
    getTransitionPayload: function (path) {
        return this.transitionPayload[path];
    }
};
