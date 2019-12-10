import React from 'react';
import logo from './hexagon.png';
import './Header.css';
import {Link} from 'react-router-dom';

const header = (props) => {
    console.log('hello', props);
    return (
    <div>
        <div className="container">
            <h1 className="headerLeft">HEXAGON</h1>  
            <img src={logo} alt="hexagon"/>
            <h1 className="headerRight">Demo Drop</h1>
            {props.loggedIn? <Link to = {props.setLoggedIn}>log out</Link>: ''}
        </div>
    </div>
    )
};

export default header;