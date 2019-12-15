import React,{useRef} from 'react';
import './SignUp.css';


function SignUp() {

    const registerRef = useRef(null);

    const doRegister = (event)=>{
        event.preventDefault();
        const data = new FormData(registerRef.current);
        fetch("/register",{
            method: "POST",
            body : data
        }).then(response => {
            if (response.status === 200) alert("Check your mail... ");
            else alert("er is iets mis gegaan!");
            }
        )
    };

    // noinspection HtmlUnknownTarget
    return (
        <div>
            <form method="post" action="/register" onSubmit={doRegister} ref={registerRef}>
            <h2 className="SignupH2">SIGN UP</h2>
                <p><input type="email" name="email" placeholder="EMAIL"/></p>
                <p><input type="password" name="password" placeholder="PASSWORD"/></p>
                <p><input type="password" name="passwordCheck" placeholder="CONFIRM PASSWORD"/></p>
                <div><p>Door op SIGNUP te klikken ga je akkoord met
            <a href="https://www.google.nl"> de algemene voorwaarden</a></p></div>
            <br/>
            <button className="SubmitButton" type="submit">SIGN UP</button>
            </form>
        </div>
    )
}

export default SignUp;