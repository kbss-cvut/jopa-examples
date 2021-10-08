import React from "react";
import {Col, Row} from "react-bootstrap";
import Constants from "../../util/Constants";
import Select from "react-select";
import {SelectOption} from "../../model/Types";
import Util from "../../util/Util";

interface RecordClassificationProps {
    types?: string[];
    onChange: (newTypes: string[]) => void;
    show: boolean;
}

const OPTIONS = Object.getOwnPropertyNames(Constants.RECORD_TYPES).map(p => ({
    label: Constants.RECORD_TYPES[p].value,
    value: Constants.RECORD_TYPES[p].value
}));

const RecordClassification: React.FC<RecordClassificationProps> = props => {
    const {types, onChange, show} = props;
    if (!show) {
        return null;
    }
    const onSelect = (opt: SelectOption | null) => {
        onChange(opt ? [opt.value!] : []);
    };
    const arrTypes = Util.sanitizeArray(types);
    const selected = OPTIONS.find(o => arrTypes.indexOf(o.value) !== -1);

    return <Row>
        <Col>
            <Select options={OPTIONS} onChange={onSelect} value={selected}/>
        </Col>
    </Row>;
};

export default RecordClassification;
