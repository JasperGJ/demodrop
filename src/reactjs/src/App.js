import React  from 'react';
import './App.css';
import Header from './Header/Header';
import LogInRegister from './LogInRegister/LogInRegister';
import LogIn from './LogIn/LogIn';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Nav from './Nav/Nav';
import SignUp from './SignUp/SignUp';
import DemoDrop from './DemoDrop/DemoDrop';
import Profile from './Profile/Profile';
import EditProfile from './EditProfile/EditProfile';
import AuthenticatedRoute from './AuthenticatedRoute/AuthenticatedRoute'
import Error from './Error/Error.js';
import Inbox from './Inbox/Inbox';

function App() {

  return (

      <div className="App">
        <Router>
          <Header />
          <Nav />

          <Switch>
            <Route path="/" exact component={LogInRegister} />
            <Route path="/login" component={LogIn} />
            <Route path="/signup">
              <SignUp />
            </Route>
            <AuthenticatedRoute path="/profile" component={Profile} />
            <AuthenticatedRoute path="/editprofile" component={EditProfile} />
            <AuthenticatedRoute path="/demodrop" component={DemoDrop} />
            {/*//todo voor onderstaande pagina moet Nav(profile etc.) van producer niet verschijnen*/}
            <AuthenticatedRoute path="/inbox" component={Inbox} />
            <Route component>
              <Error>an error occurred</Error>
            </Route>
          </Switch >
        </Router>
      </div>
  );
}

export default App;
