import React, {useRef, useState} from 'react';
import './EditProfile.css';
import Error from "../Error/Error";
import Nav from '../Nav/Nav.js';

function EditProfile(props) {
    const photoData = useRef(null);
    const profileForm = useRef(null);
    const [files, setFiles] = useState([]);

//----------------------------NIEUW -------------------------


    const [formValidation, setFormValidation] = useState({
        name: "",
        description: "",
        photo: "",
        nameError: false,
        descriptionError: false,
        photoError: true,
        formValid: true
    });

    const errors = {};

// onderstaande functie controleert of de verschillende velden (goed) zijn ingevuld

    const validateField = (key, value) => {

        if (key === "name") {
            errors.nameError = false;
            errors.formValid = errors.formValid && true;

//check of het veld 'name' niet leeg (0) is  en als zo, wordt de <p> onder 'name' input met "Name can not be empty" gevuld

            if (value.length === 0) {
                errors.name = "Artist Name cannot be empty";
                errors.nameError = true;
                errors.formValid = false;
            }
        }

        if (key === "description") {
            errors.descriptionError = false;
            errors.formValid = errors.formValid && true;

//dit checkt of minstens 20 karakters zijn ingevuld in veld 'description'

            if (value.length < 20) {
                errors.description = "A minimum of 20 characters is required";
                errors.descriptionError = true;
                errors.formValid = false;
                console.log("Check", key, errors);
            }
        }

//dit checkt of photo is geupload in veld 'photo'

        if (key === "photo") {
            errors.photoError = false;
            errors.formValid = errors.formValid && true;

            if (value.length === 0) {
                errors.photo = "Photo can not be empty";
                errors.photoError = true;
                errors.formValid = false;
            }
        }
    };

    const validateForm = (profile) => {
        errors.formValid = true;

        [...profile.entries()].map(field => validateField(field[0], field[1]));
        setFormValidation(errors);

        return errors.formValid;
    };

// ---------------------------------TOT AAN HIER NIEUW -------------------------


// wanneer submitbutton wordt geklikt, wordt onderstaande 'doProfile'' aangeroepen

    const doProfile = (event) => {
        event.preventDefault();

// const 'profile' wordt gevuld met ingevulde gegeven(name, description
        const profile = new FormData(profileForm.current);

// de foto file wordt aan 'profile' toegevoegd
        profile.append("photo", files[0]);

        if (validateForm(profile)) {

// alle gegevens versturen we met fetch en method 'post' naar server
            fetch(profileForm.current.action, {
                method: profileForm.current.method,
                body: profile
            })
                //als we reactie van server terug hebben, gaan we verder
                .then(response => {
                    if (response.status === 200) {
// als status 200, dan is goed gegaan en komt alert hiervan
                        alert("Your profile is edited!.");
                        props.location.push("/profile")
                    } else
                    {
// als niet goed, komt er alert message
                        alert("Sorry, something went wrong!")
                    }

                })
                .catch(e => console.warn(e));
        }
    };

// als foto file is geupload, zorgt onderstaande dat files met 'photodata' wordt gevuld
    function onPhotoChoosen() {
        setFiles([...photoData.current.files]);
    }

    return (
        <div>
            <Nav/>
            <h2>EDIT PROFILE</h2>

            <form method="post" action="/profile" onSubmit={doProfile} ref={profileForm}>
                <h5>Profile Photo</h5>
                <div>
                    {files.length > 0 ? <img style={{
                        margin: "10px",
                        width: "150px",
                        height: "150px"
                    }} src={URL.createObjectURL(files[0])} alt="preview profile"/> : ""}
                </div>
                <input type="file" name="photo" onChange={onPhotoChoosen} ref={photoData}/>
                <p>{formValidation.photoError && formValidation.photo}</p>
                <input type="text" name="name" placeholder="ARTIST NAME"/>
                { !formValidation.nameError ?
                    "" :
                <Error>{formValidation.nameError && formValidation.name}</Error> }
                <br/>
                <textarea name="description" placeholder="ABOUT YOURSELF(MIN 20 CHARACTERS)"/>
                { !formValidation.descriptionError ?
                    "":
                    <Error>{formValidation.descriptionError && formValidation.description}</Error>}
                <br/>
                <button className="SubmitButton" type="submit">Update Profile</button>
            </form>
        </div>
    )
}

export default EditProfile;