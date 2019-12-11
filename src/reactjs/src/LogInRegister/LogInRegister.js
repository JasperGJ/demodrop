import React from 'react';
import './LogInRegister.css';
import {Link } from 'react-router-dom';

const logInRegister = () => {
    return (
        <div className="ButtonsContainer">
            <button  className="ButtonStart"><Link to="/login" className="Link">LOGIN</Link></button>
            <br/>
            <button className="ButtonStart"><Link to="/signup" className="Link">SIGN UP</Link></button>
        </div>
    )
};

export default logInRegister;