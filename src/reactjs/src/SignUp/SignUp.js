import React, {useState} from 'react';
import './SignUp.css';
import Pdf from './Algemene_voorwaarden_Hexagon.pdf'
import Error from "../Error/Error";


function SignUp() {

    const registerRef = React.useRef(null);
    let passwordCheck = "";
    const [formValidation, setFormValidation] = useState({
        email: "",
        password: "",
        emailError: false,
        passwordError: false,
        formValid: true
    });
    const errors = {};
    console.log("formValidation", formValidation);

    const validateField = (key, value) => {

        if (key === "email") {
            errors.emailError = false;
            errors.formValid = errors.formValid && true;

            if (value.length === 0) {

                errors.email = "Email can not be empty";
                errors.emailError = true;
                errors.formValid = false;
            }
            console.log("Check", key, errors);
        }

        if (key === "password") {
            errors.passwordError = false;
            errors.formValid = errors.formValid && true;
            passwordCheck = value;

            if (value.length < 5) {
                errors.password = "Too short...write more";
                errors.passwordError = true;
                errors.formValid = false;
                console.log("Check", key, errors);
            }
        }
        if(key === "passwordCheck") {
            if(errors.passwordError) return;
            if(passwordCheck !== value) {
                errors.password = "passwords do not match";
                errors.passwordError = true;
                errors.formValid = false;
            }
        }
    };

    const validateForm = (formdata) => {
        errors.formValid = true;
        console.log("errors before", errors);

        [...formdata.entries()].map(field => validateField(field[0], field[1]));
        console.log("errors after", errors);
        setFormValidation(errors);

        return errors.formValid;
    };


    const doRegister = (event) => {
        event.preventDefault();
        const data = new FormData(registerRef.current);
        if (validateForm(data)) {

            fetch("/register", {
                method: "POST",
                body: data
            }).then(response => {
                    if (response.status === 200) alert("Check your mail... ");
                    else alert("er is iets mis gegaan!");
                }
            )
        }
    };

    // noinspection HtmlUnknownTarget
    return (
        // css van ComponentContainer staat in App.css
        <div className="ComponentContainer">
            <form method="post" action="/register" onSubmit={doRegister} ref={registerRef}>
                <h2 className="SignupH2">SIGN UP</h2>
                <p><input type="email" name="email" placeholder="EMAIL"/></p>
                { !formValidation.emailError ?
                    "" :
                    <Error>{formValidation.emailError && formValidation.email}</Error>}
                <p><input type="password" name="password" placeholder="PASSWORD"/></p>
                { !formValidation.passwordError ?
                    "" :
                    <Error>{formValidation.passwordError && formValidation.password}</Error>}
                <p><input type="password" name="passwordCheck" placeholder="CONFIRM PASSWORD"/></p>
                { !formValidation.passwordError ?
                    "" :
                    <Error>{formValidation.passwordError && formValidation.password}</Error>}
                <div><p>By clicking SIGN UP you accept the
                    <a href={Pdf} target="_blank"> terms and conditions</a></p></div>
                <br/>
                <button className="SubmitButton" type="submit">SIGN UP</button>
            </form>
        </div>
    )
}

export default SignUp;