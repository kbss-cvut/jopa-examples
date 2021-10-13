import {Route, Switch} from "react-router";
import ReportDetail from "./ReportDetail";
import ReportsController from "./ReportsController";

const ReportsRoute = () => {
    return <Switch>
        <Route path="/reports/:reportKey" component={ReportDetail}/>
        <Route exact={true} component={ReportsController}/>
    </Switch>
};

export default ReportsRoute;
