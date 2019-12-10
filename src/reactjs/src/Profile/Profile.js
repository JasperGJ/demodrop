import React from 'react';
import './Profile.css';
import foto from '../dondiablo-portret.jpg';

const profile = () => {
    return (
        <div className="Container">
            <div className="Profile">
                <img className="Profile-Photo" src={foto} alt="foto" />
                <div>
                    <h2>About</h2>
                    <p>Why a re-introduction? Because JavaScript
                    is notorious for being the world's most
                    misunderstood programming language. It is
                    often derided as being a toy, but beneath
                    its layer of deceptive simplicity, powerful
                    language features await. JavaScript is now
                    used by an incredible number of high-profil
                    applications, showing that deeper knowledge
                    of this technology is an important skill
                    for any web or mobile developer.
                    </p>
                </div>
            </div>
            <div className="Notifications">
                <h1>DJ Jake Peralta</h1>
                <div className="Notification-Content">
                    <div className="div1">one </div>
                    <div className="Demo-Date">20-12-2019 </div>
                    <div className="div3">three iiiiiiiiiiiiiiiiiiiiiiiiiiiiii</div>
                    <div className="div4"> delete demo </div>
                </div>
                <div className="Notification-Content">
                    <div className="div1">one </div>
                    <div className="Demo-Date">20-12-2019 </div>
                    <div className="div3">three iiiiiiiiiiiiiiiiiiiiiiiiiiiiii</div>
                    <div className="div4"> delete demo </div>
                </div>
                <div className="Notification-Content">
                    <div className="div1">one </div>
                    <div className="Demo-Date">20-12-2019 </div>
                    <div className="div3">three iiiiiiiiiiiiiiiiiiiiiiiiiiiiii</div>
                    <div className="div4"> delete demo </div>
                </div>
            </div>
        </div>   
     )
};

export default profile;

