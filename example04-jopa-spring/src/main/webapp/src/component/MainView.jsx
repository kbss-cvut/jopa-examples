import React from "react";
import {Col, Row} from "react-bootstrap";
import Data from "./Data";
import Students from "./Students";

const MainView = () => {
    return <Row>
        <Col xs={5}>
            <Students/>
        </Col>
        <Col xs={7}>
            <Data/>
        </Col>
    </Row>;
};

export default MainView;
