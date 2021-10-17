import React from "react";
import {usePromiseTracker} from "react-promise-tracker";
import Mask from "./Mask";

interface PromiseTrackingMaskProps {
    area: string;
    text: string;
    coverViewport?: boolean;
}

const PromiseTrackingMask: React.FC<PromiseTrackingMaskProps> = props => {
    const {promiseInProgress} = usePromiseTracker({area: props.area});

    return promiseInProgress ?
        <Mask classes={props.area && !props.coverViewport ? "mask-container" : undefined} text={props.text}/> : null;
};

export default PromiseTrackingMask;
