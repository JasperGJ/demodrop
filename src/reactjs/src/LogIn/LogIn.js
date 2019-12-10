import React, { Component } from 'react';
import AuthenticationService from '../AuthenticationService/AuthenticationService.js';
// import InputText from '../Components/InputText/InputText';
import './LogIn.css';


// const logIn = (props) => {
    class Login extends Component {

        constructor(props) {
            super(props);
            this.state = {
                username: "",
                password: "",
                hasLoginFailed: false,
                showSuccesMessage: false
            };
            this.handleChange = this.handleChange.bind(this);
            this.loginClicked = this.loginClicked.bind(this);
        }
    
    
        handleChange(event) {
            console.log(this.state);
            this.setState(
                {
                    [event.target.name]
                        : event.target.value
                }
            )
        }

    loginClicked() {
        if (this.state.username === "hexagon" && this.state.password === "hexagon") {
            AuthenticationService.registerSuccesfulLogin(this.state.username, this.state.password);
            this.props.history.push(`/profile/${this.state.username}`);
            this.setState({ showSuccesMessage: true });
            this.setState({ hasLoginFailed: false })
        }
        else {
            this.setState({ hasLoginFailed: true });
            this.setState({ showSuccesMessage: false });
        }
    }
    
    render() {
    return (
        <div>
            <h2 className="Login-H2">LOGIN</h2>
            {this.state.hasLoginFailed && <div>invalid credentials</div>}
            {this.state.showSuccesMessage && <div>login succesfull </div>}
            <div><input placeholder="USERNAME" type="text" name="username" value={this.state.username} onChange={this.handleChange} />
            <br />
            <input placeholder="PASSWORD" type="password" name="password" value={this.state.password} onChange={this.handleChange} />
            </div>
            <a href="https://www.google.nl" target="_blank">forgot password?</a>
            <br/>
            <button onClick={this.loginClicked}>Login</button>
        </div>

        // <div>
        //     <h2 className="Login-H2">LOGIN</h2>
        //     {this.state.hasLoginFailed && <div>invalid credentials</div>}
        //     {this.state.showSuccesMessage && <div>login succesfull </div>}
        //     <div><InputText type="text" name="username" placeholder="USERNAME" onChange={this.handleChange} />
        //     <br />
        //     <InputText type="password" name="password" placeholder="PASSWORD" onChange={this.handleChange} />
        //     </div>
        //     <a href="https://www.google.nl" target="_blank">forgot password?</a>
        //     <br/>
        //     <button onClick={this.loginClicked}>Login</button>
        //     {/* <Button className="Login-Button" onClick={this.loginClicked}><Link to="/profile">LOGIN</Link></Button>*/}
        //  </div>
    )
    }
}

export default Login;