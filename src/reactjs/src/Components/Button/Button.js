import React from 'react';
import './Button.css';

const Button = (props) => {
    return <button className="SubmitButton" type="submit">{props.children}</button>
};

export default Button;