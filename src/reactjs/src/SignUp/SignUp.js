import React from 'react';
import InputText from '../Components/InputText/InputText';
import Button from '../Components/Button/Button';
import './SignUp.css';
import {Link } from 'react-router-dom';


const signUp = () => {
    return (
        <div>
            <h2 className="Signup-H2">SIGN UP</h2>
            <InputText className="Input-Signup" placeholder="USERNAME" />
            <br />
            <InputText className="Input-Signup" placeholder= "EMAIL"/>
            <br/>
            <InputText className="Input-Signup" placeholder="PASSWORD" />
            <br />
            <InputText className="Input-Signup" placeholder= "CONFIRM PASSWORD"/>
            <div><p>Door op SIGNUP te klikken ga je akkoord met 
            <a href="https://www.google.nl"> de algemene voorwaarden</a></p></div>
            <br/>
            <Button><Link to="/editprofile">SIGN UP</Link></Button>
        </div>
    )
};

export default signUp;