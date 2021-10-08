import {Col, Container, Nav, Navbar, NavItem, NavLink, Row} from "react-bootstrap";
import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import AppModel from "../model/AppModel";
import {loadSettings} from "../action/AsyncActions";
import Constants from "../util/Constants";
import {Link} from "react-router-dom";
import {Route, Switch} from "react-router";
import Data from "./Data";
import Settings from "./Settings";
import ReportsController from "./report/ReportsController";

const MainView: React.FC = (props) => {
    const dispatch = useDispatch();
    const storage = useSelector((state: AppModel) => state.settings[Constants.REPOSITORY_TYPE_PARAM]);
    useEffect(() => {
        dispatch(loadSettings(Constants.REPOSITORY_TYPE_PARAM));
    }, [dispatch]);

    return <div>
        <header>
            <Navbar bg="light" expand="lg">
                <Navbar.Brand>JOPA ESWC 2016</Navbar.Brand>
                <Nav>
                    <NavItem><NavLink href="#/audits">Audits</NavLink></NavItem>
                    <NavItem><NavLink href="#/reports">Reports</NavLink></NavItem>
                    <NavItem><NavLink href="#/data">Data</NavLink></NavItem>
                    <NavItem><NavLink href="#/settings">Settings</NavLink></NavItem>
                </Nav>
            </Navbar>
        </header>
        <section>
            <Container fluid={true}>
                <Switch>
                    <Route path='/data' component={Data}/>
                    <Route path='/settings' component={Settings}/>
                    <Route path="/reports" component={ReportsController}/>
                </Switch>
            </Container>
        </section>
        <footer>
            <Row>
                <Col>
                    <div>
                        <Link to="settings" title='View repository type information'>{storage}</Link>
                    </div>
                </Col>
            </Row>
        </footer>
    </div>;
};

export default MainView;
