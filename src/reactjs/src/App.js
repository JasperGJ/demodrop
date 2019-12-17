import React, {useState,useEffect} from 'react';
import './App.css';
import Header from './Header/Header';
import LogInRegister from './LogInRegister/LogInRegister';
import LogIn from './LogIn/LogIn';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import SignUp from './SignUp/SignUp';
import DemoDrop from './DemoDrop/DemoDrop';
import Profile from './Profile/Profile';
import EditProfile from './EditProfile/EditProfile';
import AuthenticatedRoute from './AuthenticatedRoute/AuthenticatedRoute'
import Error from './Error/Error.js';
import Inbox from './Inbox/Inbox';
import Admin from "./Admin/Admin";
import ChangePassword from "./ChangePassword/ChangePassword";

function App() {

  return (

      <div className="App">
        <Router>
          <Header  />

          <Switch>
            <Route path="/" exact component={LogInRegister} />
            <Route path="/index.html" exact component={LogInRegister} />
            <Route path="/loginform" component={LogIn} />
            <Route path="/logout" component={LogIn} />
            <Route path="/change" component={ChangePassword}/>
            <Route path="/signup">
              <SignUp />
            </Route>
            <AuthenticatedRoute path="/profile" component={Profile} />
            <AuthenticatedRoute path="/editprofile" component={EditProfile} />
            <AuthenticatedRoute path="/demodrop" component={DemoDrop} />
            {/*//todo voor onderstaande pagina moet Nav(profile etc.) van producer niet verschijnen*/}
            <AuthenticatedRoute path="/backoffice" component={Inbox} />
            <AuthenticatedRoute path="/admin" component={Admin} />
            <Route>
              <Error>an error occurred</Error>
            </Route>
          </Switch >
        </Router>
      </div>
  );
}

export default App;
