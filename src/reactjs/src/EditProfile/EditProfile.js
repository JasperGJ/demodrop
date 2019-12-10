import React from 'react';
import foto from '../dondiablo-portret.jpg';
import './EditProfile.css';
import InputText from '../Components/InputText/InputText';

const editProfile = () => {
    return (
        <div>
            <h2>EDIT PROFILE</h2>
            <img className="Edit-Profile-Photo" src={foto} alt="foto" />
            <InputText className="Input-Login" placeholder="ARTIST NAME"/>
            <br/>
            <textarea placeholder="Type your BIO"/>
            <br/>
            <button type="submit">SAVE</button>
        </div>
    )
};

export default editProfile;