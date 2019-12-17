import React, { Component } from 'react';
import AuthenticationService from '../AuthenticationService/AuthenticationService.js';
import { Route, Redirect } from 'react-router-dom';


class AuthenticatedRoute extends Component {
    render() {
        if (AuthenticationService.isUserLoggedIn()) {
            return <Route {...this.props} />
        } else {
            return <Redirect to="/loginform" />
        }
    }
}

export default AuthenticatedRoute;