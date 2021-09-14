import React from "react";
import {Form, FormControlProps} from "react-bootstrap";

interface InputProps extends FormControlProps{
    label?: string;
}

const Input: React.FC<InputProps> = props => {
    const {label, ...inputProps} = props;
    return <Form.Group>
        {label && <Form.Label>{label}</Form.Label>}
        <Form.Control {...inputProps}/>
    </Form.Group>
};

export default Input;
