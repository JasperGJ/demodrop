import React, {useState,useRef,useEffect} from "react";
import './Admin.css'

function Admin(){

    useEffect(()=>{
        gettemplates();
        },[]);

    /*Create Backoffice user*/
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

    /*Templates*/
    const [comment,setComments] = useState("");
    const [status,setStatus] = useState("ACCEPT");

    const [templates,setTemplates] = useState([]);

    const select = (event) => {
        setStatus(event.target.value);
    };

    const handleChangeComments = (event) => {
        setComments(event.target.value);
    };

    const insertTemplate= () => {
        const data = new FormData();
        data.append("comment",comment);
        data.append("status",status);
        fetch("/template/0",{
            method: "POST",
            body : data
        }).then(response => {
                if (response.status === 200) alert("Template is added... ");
                else alert("Something went wrong!");
            }
        )

    };

    const delTemplate = (id) => {
        fetch("/template/"+id, {
            method: "DELETE"
        }).then(response => {
            if (response.status === 200) alert("Template is deleted... ");
            else alert("Something went wrong!");
        }).catch(e => console.warn(e))

    };

    const gettemplates = () => {
        fetch("/templates")
            .then(responce => {
                if (responce.status === 200) return responce.json();
            })
            .then(json => {
                console.log(templates);
                setTemplates(json);

            })
            .catch(e => console.warn(e));
    };
    console.log(templates);
    return (
        <div className="ComponentContainerAdmin">
            <div>
                <div className="ComponentContainer">
                    <form method="post" action="/createUser" onSubmit={createUser} ref={createUserRef}>
                        <h2 className="SignupH2">Create Backoffice User</h2>
                        <p><input type="email" name="email" placeholder="EMAIL"/></p>
                        <p><input type="password" name="password" placeholder="PASSWORD"/></p>
                        <button className="SubmitButton" type="submit">Create</button>
                    </form>
                </div>
            </div>

            <hr/>

            <div>
                <div className="title">

                    <h2>Manage Comments</h2>
                </div>
                <div key="0" className="container">
                    <div className="section"><input type="text" name="comment" value={comment} onChange={handleChangeComments}/></div>
                    <div className="section"><select id="SelectMessage" onChange={select}>
                        <option key="0">ACCEPT</option>,
                        <option key="1">DECLINE</option>
                    </select></div>
                    <div className="section">
                        <button onClick={insertTemplate}>insert</button>
                    </div>
                </div>
                {templates.map((template) =>
                <div key={template.id} className="container">
                    <div className="section"><input value={template.comment}/></div>
                    <div className="section"><input value={template.status}/></div>
                    <div className="section">
                        <button onClick={() => delTemplate(template.id) }>delete</button>
                    </div>
                </div>
                )}

            </div>
        </div>
    )
}

export default Admin;