import React from 'react';
import logo from './hexagon.png';
import './Header.css';
import {Link} from 'react-router-dom';
import AuthenticationService from "../AuthenticationService/AuthenticationService";

const header = (props) => {
    console.log('hello', props);
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    return (
    <div>
        <div className="ContainerHeader">
            <h1 className="HeaderLeft">HEXAGON</h1>
            <img src={logo} alt="hexagon"/>
            <h1 className="HeaderRight">Demo Drop</h1>
            {/*{props.loggedIn? <Link to = {props.setLoggedIn}>log out</Link>: ''}*/}
            <ul className="LogLinks">
                {!isUserLoggedIn && <li><Link to="/login">Login</Link></li>}
                {isUserLoggedIn && <li><Link to="/logout" onClick={AuthenticationService.logout}>Logout</Link></li>}
            </ul>
        </div>
    </div>
    )
};

export default header;