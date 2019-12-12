import React, {useRef, useState} from 'react';
import './EditProfile.css';
import Error from "../Error/Error";

function EditProfile() {
    const photoData = useRef(null);
    const profileForm = useRef(null);
    const [files, setFiles] = useState([]);

//----------------------------NIEUW -------------------------


    const [formValidation, setFormValidation] = useState({
        name: "",
        description: "",
        photo: "",
        nameError: true,
        descriptionError: true,
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
                errors.name = "name can not be empty";
                errors.nameError = true;
                errors.formValid = false;
            }
            console.log("Check", key, errors);
        }

        if (key === "description") {
            errors.descriptionError = false;
            errors.formValid = errors.formValid && true;

//dit checkt of minstens 20 karakters zijn ingevuld in veld 'description'

            if (value.length < 20) {
                errors.description = "Too short...write more";
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
                console.log("Check length", key, errors);
            }
        }
    };

    const validateForm = (profile) => {
        errors.formValid = true;
        console.log("errors before", errors);

        [...profile.entries()].map(field => validateField(field[0], field[1]));
        console.log("errors after", errors);
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
                    console.log("getProfile", response);

                    if (response.status === 200) {
// als status 200, dan is goed gegaan en gaan we naar profile pagina
                        alert("Gegevens zijn goed verwerkt.")
                        // TODO: redirect naar homepage
                        window.location.href ="/profile";

// als niet goed, komt er alert message
                        alert("er is iets mis gegaan")
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
                    <form method="post" action="/profile" onSubmit={doProfile} ref={profileForm}>
                        <p><input type="text" name="name" placeholder="enter name"/></p>
                        <Error>{formValidation.nameError && formValidation.name}</Error>
                        <p><textarea name="description" placeholder="enter description"/></p>
                        <Error>{formValidation.descriptionError && formValidation.description}</Error>
                        <p><input type="file" name="photo" onChange={onPhotoChoosen} ref={photoData}/></p>
                        <p>{formValidation.photoError && formValidation.photo}</p>
                        <p>
                            <button type="submit">Update Profile</button>
                        </p>
                    </form>
                </div>
            </div>
        )
    }

    export default EditProfile;