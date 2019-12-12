import React, {useRef,useState,useEffect}  from 'react';
import foto from '../dondiablo-portret.jpg';
import './EditProfile.css';
import InputText from '../Components/InputText/InputText';
import Button from "../Components/Button/Button";

function EditProfile(){
    const photoData = useRef(null);
    const profileForm = useRef(null);
    const [files,setFiles] = useState([]);

    const doProfile = (event) => {
        event.preventDefault();
        const profile = new FormData(profileForm.current);
        profile.append("photo",files[0]);
        fetch(profileForm.current.action, {
            method: profileForm.current.method,
            body: profile
        })
            .then(response => {
                console.log("getProfile", response);
                const contentType = response.headers.get('content-type');
                if (contentType && contentType.includes('application/json'))
                    return response.json();
                return response.text();
            })
            .then(json => {} )
            .catch(e => console.warn(e));

    };


    function onPhotoChoosen() {
        setFiles([...photoData.current.files]);
    }

    return (
        <div>
            <h2>EDIT PROFILE</h2>
            <div>
                <h1>Edit Profile</h1>
                <div>
                    {files.length > 0 ? <img style={{
                        margin: "10px",
                        width: "200px",
                        height: "200px"
                    }} src={URL.createObjectURL(files[0])} alt="preview profile"/> : ""}
                </div>
                <form method="post" action="/profile"onSubmit={doProfile} ref={profileForm}>
                    <p><input type="text" name="name" placeholder="enter name"/></p>
                    <p><input type="text" name="description" placeholder="enter description"/></p>
                    <p><input type="file" name="photo" onChange={onPhotoChoosen} ref={photoData}/></p>
                    <p><button type="submit">Update Profile</button></p>
                </form>
            </div>
        </div>
    )
};

export default EditProfile;