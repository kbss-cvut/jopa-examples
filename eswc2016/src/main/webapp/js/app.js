'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import createHashHistory from 'history/lib/createHashHistory';
import {IndexRoute, Router, Route} from 'react-router';

import MainView from './components/MainView';
import AuditsController from './components/AuditsController';
import AuditController from './components/AuditController';
import ReportsController from './components/ReportsController';
import ReportController from './components/ReportController';
import Data from './components/Data';
import Settings from './components/Settings';
import Routing from './util/Routing';

/**
 * This is the application's entry point.
 *
 * The application is written using the new ES6 syntax (for the most part).
 */
class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Router history={Routing.getHistory()}>
                <Route path='/' component={MainView}>
                    <IndexRoute component={AuditsController}/>
                    <Route path='audits' component={AuditsController}/>
                    <Route path='audits/create' component={AuditController}/>
                    <Route path='audits/:auditKey' component={AuditController}/>
                    <Route path='reports' component={ReportsController}/>
                    <Route path='reports/create' component={ReportController}/>
                    <Route path='reports/:reportKey' component={ReportController}/>
                    <Route path='data' component={Data}/>
                    <Route path='settings' component={Settings}/>
                </Route>
            </Router>);
    }
}

ReactDOM.render(<App/>, document.getElementById('content'));
