import React from 'react';
import './LogInRegister.css';
import {Link } from 'react-router-dom';

const logInRegister = () => {
    return (
        <div className="ButtonsContainer">
            <Link to="/loginform" className="Link"><button  className="ButtonStart">LOGIN</button></Link>
            <br/>
            <Link to="/signup" className="Link"><button className="ButtonStart">SIGN UP</button></Link>
        </div>
    )
};

export default logInRegister;