import React from 'react';
import logo from './hexagon.png';
import './Header.css';
import {Link} from 'react-router-dom';
import AuthenticationService from "../AuthenticationService/AuthenticationService";

const header = (props) => {
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();

    return (
    <div>
        <div className="ContainerHeader">
            <h1 className="HeaderLeft">HEXAGON</h1>
            <div className="LogoLink">
                <a href="/index.html"><img className="HexagonImg" src={logo} alt="hexagon"/></a>
            </div>
            <h1 className="HeaderRight">DEMO DROP</h1>

            {/*{props.loggedIn? <Link to = {props.setLoggedIn}>log out</Link>: ''}*/}
            {/*<div className="LogLinksContainer">*/}
            <div>
                {!isUserLoggedIn && <Link to="/loginform" className="LogLinks">Login</Link>}
                {isUserLoggedIn && <Link to="/logout" className="LogLinks" onClick={AuthenticationService.logout}>Logout</Link>}
            </div>
        </div>
    </div>)
};

export default header;