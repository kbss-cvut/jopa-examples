'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import createHashHistory from 'history/lib/createHashHistory';
import {IndexRoute, Router, Route} from 'react-router';

import MainView from './components/MainView';
import AuditsController from './components/AuditsController';
import AuditController from './components/AuditController';
import Data from './components/Data';
import Routing from './util/Routing';

/**
 * This is the application's entry point.
 *
 * The application is written using the new ES6 syntax (for the most part).
 */
class App extends React.Component {
    constructor() {
        super();
    }

    render() {
        return (
            <Router history={Routing.getHistory()}>
                <Route path='/' component={MainView}>
                    <IndexRoute component={AuditsController}/>
                    <Route path='audits' component={AuditsController}/>
                    <Route path='audits/create' component={AuditController}/>
                    <Route path='audits/:auditKey' component={AuditController}/>
                    <Route path='data' component={Data}/>
                </Route>
            </Router>);
    }
}

ReactDOM.render(<App/>, document.getElementById('content'));
