import React from 'react';
import foto from '../dondiablo-portret.jpg';
import './EditProfile.css';
import InputText from '../Components/InputText/InputText';
import Button from "../Components/Button/Button";

const editProfile = () => {
        return (
        <div>
            <h2>EDIT PROFILE</h2>
            <img className="EditProfilePhoto" src={foto} alt="foto" />
            <InputText  placeholder="ARTIST NAME"/>
            <br/>
            <textarea placeholder="Type your BIO"/>
            <br/>
            <Button type="submit">SAVE</Button>
        </div>
    )
};

export default editProfile;