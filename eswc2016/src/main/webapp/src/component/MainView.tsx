import {Container, Nav, Navbar, NavItem, NavLink} from "react-bootstrap";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import AppModel from "../model/AppModel";
import {loadSettings} from "../action/AsyncActions";
import Constants from "../util/Constants";
import {Link} from "react-router-dom";
import {Route, Switch} from "react-router";
import Data from "./Data";
import Settings from "./Settings";
import ReportsRoute from "./report/ReportsRoute";
import AuditsRoute from "./audit/AuditsRoute";
import Messages from "./message/Messages";

const MainView = () => {
    const dispatch = useDispatch();
    const storage = useSelector((state: AppModel) => state.settings[Constants.REPOSITORY_TYPE_PARAM]);
    useEffect(() => {
        dispatch(loadSettings(Constants.REPOSITORY_TYPE_PARAM));
    }, [dispatch]);

    return <>
        <header className="mb-5">
            <Navbar bg="light" expand="lg">
                <Container>
                    <Navbar.Brand href="#/reports">JOPA ESWC 2016</Navbar.Brand>
                    <Nav className="me-auto">
                        <NavItem><NavLink href="#/audits">Audits</NavLink></NavItem>
                        <NavItem><NavLink href="#/reports">Reports</NavLink></NavItem>
                        <NavItem><NavLink href="#/data">Data</NavLink></NavItem>
                        <NavItem><NavLink href="#/settings">Settings</NavLink></NavItem>
                    </Nav>
                </Container>
            </Navbar>
        </header>
        <main className="flex-shrink-0 mb-5">
            <Messages/>
            <Container fluid={true}>
                <Switch>
                    <Route path='/data' component={Data}/>
                    <Route path='/settings' component={Settings}/>
                    <Route path="/audits" component={AuditsRoute}/>
                    <Route path="/reports" component={ReportsRoute}/>
                    <Route exact={true} component={ReportsRoute}/>
                </Switch>
            </Container>
        </main>
        <footer className="footer mt-auto py-3 bg-light">
            <Container fluid={true} className="text-end">
                <Link to="settings" title='View repository type information'>{storage}</Link>
            </Container>
        </footer>
    </>;
};

export default MainView;
