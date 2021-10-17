import * as React from "react";
import {useCallback, useEffect} from "react";
import {Message as MessageModel} from "../../model/Types";
import {Alert} from "react-bootstrap";
import {useDispatch} from "react-redux";
import {dismissMessage} from "../../action/SyncActions";
import Constants from "../../util/Constants";

interface MessageProps {
    message: MessageModel;
}

export const Message: React.FC<MessageProps> = (props: MessageProps) => {
    const {message} = props;
    const dispatch = useDispatch();
    const dismiss = useCallback(
        () => dispatch(dismissMessage(message)),
        [message, dispatch]
    );
    useEffect(() => {
        const timer = setTimeout(() => {
            dismiss();
        }, Constants.MESSAGE_DURATION);
        return () => clearTimeout(timer);
    }, [dismiss, message]);

    return (
        <Alert variant={message.type} show={true} dismissible={true} onClose={dismiss}>
            {message.message}
        </Alert>
    );
};

export default Message;
