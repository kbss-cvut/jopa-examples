'use strict';

var React = require('react');
var ReactDOM = require('react-dom');
var TestUtils = require('react-addons-test-utils');


module.exports = {

    /**
     * Renders the specified component.
     * @param component Component to render
     * @return {*|!ReactComponent} The rendered component
     */
    render: function (component) {
        return TestUtils.renderIntoDocument(component);
    },

    /**
     * Finds component with the specified text.
     * @param root Root of the tree where the component is searched for
     * @param component Component class
     * @param text Component text
     */
    getComponentByText: function (root, component, text) {
        var components = TestUtils.scryRenderedComponentsWithType(root, component);
        return this._getNodeByText(components, text, true);
    },

    _getNodeByText: function (components, text, strict) {
        for (var i = 0, len = components.length; i < len; i++) {
            var node = ReactDOM.findDOMNode(components[i]);
            if (strict && node.textContent === text) {
                return node;
            } else if (!strict && node.textContent.indexOf(text) !== -1) {
                return node;
            }
        }
        return null;
    },

    /**
     * Finds component with the specified text.
     *
     * This version searches components by tag, so it will catch also simple components like div.
     * @param root Root of the tree where the component is searched for
     * @param tag Tag name
     * @param text Component text
     */
    getComponentByTagAndText: function (root, tag, text) {
        var components = TestUtils.scryRenderedDOMComponentsWithTag(root, tag);
        return this._getNodeByText(components, text, true);
    },

    /**
     * Finds component which contains the specified text.
     *
     * This version searches components by tag, so it will catch also simple components like div.
     * @param root Root of the tree where the component is searched for
     * @param tag Tag name
     * @param text Text contained in the component's text content
     */
    getComponentByTagAndContainedText: function (root, tag, text) {
        var components = TestUtils.scryRenderedDOMComponentsWithTag(root, tag);
        return this._getNodeByText(components, text, false);
    },

    /**
     * Creates a mock objects with the specified methods.
     *
     * The methods adhere to the builder pattern - they return the mock instance itself.
     * @param reqMockMethods array of methods to mock
     */
    mockRequestMethods: function (reqMockMethods) {
        var reqMock = jasmine.createSpyObj('request', reqMockMethods);
        for (var i = 0; i < reqMockMethods.length; i++) {
            // All mock methods just return the instance to adhere to the builder pattern implemented by request
            reqMock[reqMockMethods[i]].and.callFake(function () {
                return reqMock;
            });
        }
        return reqMock;
    }
};
