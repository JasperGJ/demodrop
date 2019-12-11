import React, {Component} from 'react';
import { Link } from 'react-router-dom';
import './Nav.css';
import AuthenticationService from '../AuthenticationService/AuthenticationService.js';
import { withRouter } from 'react-router-dom';


// const nav = () => {
    class Nav extends Component {

        render() {
    const isUserLoggedIn = AuthenticationService.isUserLoggedIn();
    console.log("userloggedin" + isUserLoggedIn);

    return (
        <nav>
            <div className="Nav">
                <ul>
                    {isUserLoggedIn && <li> <Link to="/profile" >Profile</Link></li>}
                    {isUserLoggedIn && <li> <Link to="/editprofile" >Edit Profile</Link></li>}
                    {isUserLoggedIn && <li> <Link to="/demodrop">Demo Drop</Link></li>}
                </ul>
            </div>
        </nav>
    )
};
    }

export default withRouter(Nav);