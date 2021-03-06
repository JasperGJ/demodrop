import React, {Component} from 'react';
import {NavLink} from 'react-router-dom';
import './Nav.css';
import {withRouter} from 'react-router-dom';


class Nav extends Component {

    render() {

        return (
            <nav>
                <div className="Nav">
                    <ul>
                        <li><NavLink className="NavItem" activeClassName="selected" to="/profile" exact="true">Profile</NavLink></li>
                        <li><NavLink className="NavItem" activeClassName="selected" to="/editprofile" exact="true">Edit Profile</NavLink></li>
                        <li><NavLink className="NavItem" activeClassName="selected" to="/demodrop">Demo Drop</NavLink>
                        </li>
                    </ul>
                </div>
            </nav>
        )
    };
}

export default withRouter(Nav);