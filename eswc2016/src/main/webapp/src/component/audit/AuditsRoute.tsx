import {Route, Switch} from "react-router";
import AuditController from "./AuditController";
import Audits from "./Audits";

const AuditsRoute = () => {
    return <Switch>
        <Route path="/audits/create" component={AuditController}/>
        <Route path="/audits/:auditKey" component={AuditController}/>
        <Route component={Audits}/>
    </Switch>;
};

export default AuditsRoute;
