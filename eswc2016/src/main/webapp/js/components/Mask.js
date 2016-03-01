'use strict';

import React from 'react';
import {ClipLoader} from 'halogen';

export default (props) => {
    var text = props.text ? props.text : 'Please Wait...';
    return (
        <div className='mask'>
            <div className='spinner-container'>
                <div style={{width: 32, height: 32, margin: 'auto'}}>
                    <ClipLoader color='#337ab7' size='32px'/>
                </div>
                <div className='spinner-message'>{text}</div>
            </div>
        </div>
    );
};
