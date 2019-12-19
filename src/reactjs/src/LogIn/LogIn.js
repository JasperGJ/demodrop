import React, {Component} from 'react';
import AuthenticationService from '../AuthenticationService/AuthenticationService.js';
import './LogIn.css';

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            hasLoginFailed: false
            // showSuccesMessage: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.loginClicked = this.loginClicked.bind(this);
        this.resetPassword = this.resetPassword.bind(this);
    }


    handleChange(event) {
        console.log("handleChange", this.state);
        this.setState(
            {
                [event.target.name]: event.target.value
            }
        )
    }

    loginClicked() {
        const data = new FormData();
        data.append("username", this.state.username);
        data.append("password", this.state.password);

        fetch("/login", {
            method: "POST",
            body: data
        }).then(response => {
            console.log("response", response)
            if (response.status === 200) {
                return response.json();
            }
            this.setState({hasLoginFailed: true});
            // this.setState({showSuccesMessage: false});
        })
            .then(json => {
                AuthenticationService.registerSuccesfulLogin(this.state.username, json.role);
                this.props.history.push(`${json.role}`);
                // this.setState({showSuccesMessage: true});
                this.setState({hasLoginFailed: false})
            })
            .catch(e => console.warn(e))
    }

    resetPassword() {
        console.log("reset")
        fetch("/reset?email=" + this.state.username)
            .then(responce => {
                alert("Check your mail");
                this.props.history.push("/");
            });
    }

    render() {
        return (
            // css van ComponentContainer staat in App.css
            <div className="ComponentContainer">
                <h2 className="Login-H2">LOGIN</h2>
                {this.state.hasLoginFailed ? <div>invalid credentials</div> : ""}
                <div><input placeholder="EMAIL" type="email" name="username" value={this.state.username}
                            onChange={this.handleChange}/>
                    <br/>
                    <input placeholder="PASSWORD" type="password" name="password" value={this.state.password}
                           onChange={this.handleChange}/>
                </div>
                <button className="buttonlink" onClick={this.resetPassword}>forgot password?</button>
                <br/>
                <button className="SubmitButton" onClick={this.loginClicked}>Login</button>
            </div>
        )
    }
}

export default Login;