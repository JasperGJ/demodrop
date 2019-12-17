import React, {useState, useEffect} from 'react';
import './Profile.css';
import foto from '../Header/hexagon.png';
import Nav from '../Nav/Nav.js';



const Profile = () => {

    const [profile, setProfile] = useState({
        loaded: false,
        isLoading: false,
        data: {}
    });

    useEffect(
        () => {
            getProfile();
        },
        [],
    );

    const getProfile = () => {
        setProfile(
            {
                loaded: false,
                isLoading: true,
                data: {}
            });
        fetch("/profile")
            .then(response => {
                console.log("JSON =", response);
                if (response.status === 200)
                    return response.json();
                else return alert("er is iets misgegaan");
            })
            .then(json => {
                console.log("Json =", json);
                setProfile({loaded: true, isLoading: false, data: json});
            });
    };
    console.log("profile", profile);

    return (

        <div>
            <Nav/>

            {profile.loaded ?
                <div className="Container">
                    <div>
                        <img className="Profile-Photo" src={`data:image/png;base64,${profile.data.photo}`}
                             alt="profile "/>
                        <div className="Profile">
                            <h2 >About</h2>
                            <p className="About">
                                {profile.data.description}
                            </p>
                        </div>
                    </div>
                    <div className="Notifications">
                        <h1>
                            {profile.data.name}
                        </h1>
                        {profile.data.messages.map((message, index) =>
                            <div key={index} className="Notification-Content">
                                <img src={foto} alt="hexagon thumbnail" className="HexagonThumbnail"/>
                                <div className="Demo-Date">{message.date}</div>
                                <div className="div3">{message.text}</div>
                                <div className="div4"> delete demo</div>
                            </div>
                        )}

                    </div>
                </div>
                :
                <div>
                    loading
                </div>
            }
        </div>
    )
};


export default Profile;
