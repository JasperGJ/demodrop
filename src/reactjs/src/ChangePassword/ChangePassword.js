import React,{useRef,useEffect,useState} from 'react';


function ChangePassword(props) {

    const registerRef = useRef(null);
    const [params,setParams] = useState({})
useEffect(()=>{
    console.log("props",props);
    const sParams = new URLSearchParams(props.location.search);
    setParams({token : sParams.get('token'),email : sParams.get('email')});


},[props])
     const doRegister = (event)=>{
        event.preventDefault();
        const data = new FormData(registerRef.current);
        data.append("token",params.token);
        fetch("/changepassword",{
            method: "POST",
            body : data
        }).then(response => {
                if (response.status === 200) alert("Check your mail... ");
                else alert("er is iets mis gegaan!");
            }
        )
    };

    return (
        <div>
            <form method="post" action="/changepassword" onSubmit={doRegister} ref={registerRef}>
                <h2 className="Signup">Change Password</h2>
                <p><input readOnly value={params.email} /></p>
                <p><input type="password" name="password" placeholder="enter password"/></p>
                <p><input type="password" name="passwordCheck" placeholder="enter same password"/></p>
                <button type="submit">CHANGE</button>
            </form>
        </div>
    )
}

export default ChangePassword;