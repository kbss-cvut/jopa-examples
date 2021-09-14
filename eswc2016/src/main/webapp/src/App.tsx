import * as ReactDOM from 'react-dom';
import {Route, Router, Switch} from 'react-router';

import MainView from './components/MainView';
import AuditsController from './components/AuditsController';
import AuditController from './components/AuditController';
import ReportsController from './components/ReportsController';
import ReportController from './components/ReportController';
import Data from './component/Data';
import Settings from './component/Settings';
import Routing from './util/Routing';
import {Provider} from "react-redux";
import AppStore from "./store/AppStore";

const App = () => {
    return <Provider store={AppStore}>
        <Router history={Routing.history}>
            <Switch>
                <Route path='/' component={MainView}>
                    <Route path='audits' component={AuditsController}/>
                    <Route path='audits/create' component={AuditController}/>
                    <Route path='audits/:auditKey' component={AuditController}/>
                    <Route path='reports' component={ReportsController}/>
                    <Route path='reports/create' component={ReportController}/>
                    <Route path='reports/:reportKey' component={ReportController}/>
                    <Route path='data' component={Data}/>
                    <Route path='settings' component={Settings}/>
                </Route>
            </Switch>
        </Router>
    </Provider>
};

ReactDOM.render(<App/>, document.getElementById('content'));
