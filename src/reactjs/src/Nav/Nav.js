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
            <div>
                <ul>
                    {isUserLoggedIn && <li> <Link to="/profile">Profile</Link></li>}
                    {isUserLoggedIn && <li> <Link to="/editprofile">Edit Profile</Link></li>}
                    {isUserLoggedIn && <li> <Link to="/demodrop">Demo Drop</Link></li>}
                </ul>
                <ul className="navbar-nav navbar-collapse justify-content-end">
                    {!isUserLoggedIn && <li><Link className="nav-link" to="/login">Login</Link></li>}
                    {isUserLoggedIn && <li><Link className="nav-link" to="/logout" onClick={AuthenticationService.logout} >Logout</Link></li>}
                </ul>
            </div>
        </nav>
    )
};
    }

export default withRouter(Nav);