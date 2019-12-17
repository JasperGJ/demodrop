import React, {useRef} from "react";

function Admin(){

    const createUserRef = useRef(null);

    const createUser = (event)=>{
        event.preventDefault();
        const data = new FormData(createUserRef.current);
        fetch("/createuser",{
            method: "POST",
            body : data
        }).then(response => {
                if (response.status === 200) alert("Check your mail... ");
                else alert("Something went wrong!");
            }
        )
    };


    return (
        // css in App.css
        <div className="ComponentContainerAdmin">
            <h1>Admin Page</h1>

            <div className="ComponentContainerAdmin">
                <form method="post" action="/createuser" onSubmit={createUser} ref={createUserRef}>
                    <h2 className="SignupH2">Create Backoffice User</h2>
                    <p><input type="email" name="email" placeholder="EMAIL"/></p>
                    <p><input type="password" name="password" placeholder="PASSWORD"/></p>
                    <div><p>By clicking SIGNUP you accept the terms and conditions
                        <a href="https://www.google.nl"> terms and conditions</a></p></div>
                    <br/>
                    <button className="SubmitButton" type="submit">Create</button>
                </form>
            </div>
        </div>
    )
}

export default Admin;

