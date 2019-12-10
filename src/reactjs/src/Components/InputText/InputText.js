import React from 'react';
import './InputText.css';

const inputText = (props) => {
    return <input type={props.type} name={props.name} placeholder={props.placeholder}/>
};

export default inputText;