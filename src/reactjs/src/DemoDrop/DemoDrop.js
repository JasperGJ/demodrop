import React, {useState, useRef} from 'react';
import './DemoDrop.css';
import Error from '../Error/Error.js';
import Nav from '../Nav/Nav'


function DemoDrop() {

    const demoForm = useRef();
    const audioData = useRef([]);
    const [audioFiles, setAudioFiles] = useState([]);

    const [formValidation, setFormValidation] = useState({
        name: "",
        description: "",
        audio: "",
        nameError: false,
        descriptionError: false,
        audioError: false,
        formValid: true
    });
    const errors = {};
    console.log("formValidation", formValidation);

    const validateField = (key, value) => {

        if (key === "name") {
            errors.nameError = false;
            errors.formValid = errors.formValid && true;

            if (value.length === 0) {

                errors.name = "Song Name can not be empty";
                errors.nameError = true;
                errors.formValid = false;
            }
            console.log("Check", key, errors);
        }

        if (key === "description") {
            errors.descriptionError = false;
            errors.formValid = errors.formValid && true;

            if (value.length < 20) {
                errors.description = "A minimum of 20 characters is required";
                errors.descriptionError = true;
                errors.formValid = false;
                console.log("Check", key, errors);
            }

        }
        if (key === "audio") {
            errors.audioError = false;
            errors.formValid = errors.formValid && true;

            if (value.name.length === 0) {
                errors.audio = "Audio can not be empty";
                errors.audioError = true;
                errors.formValid = false;
                console.log("Check length", key, errors);
            }

            if (value.type !== "audio/mp3") {
                errors.audio = "Wrong audio format " + value.type + " is wrong";
                errors.audioError = true;
                errors.formValid = false;
                console.log("Check type", key, errors);
            }
        }
    };

    const validateForm = (formdata) => {
        errors.formValid = true;
        console.log("errors before", errors);

        [...formdata.entries()].map(field => validateField(field[0], field[1]));
        console.log("errors after", errors);
        setFormValidation(errors);

        return errors.formValid;
    };

    const doDropDemo = (event) => {

        event.preventDefault();

        const formdata = new FormData(demoForm.current);

        // als uit de functie validateForm blijkt dat een veld niet juist is ingevuld, dan stopt het hier. Als wel goed, dan gaat de functie verder
        if (validateForm(formdata)) {

            fetch(demoForm.current.action, {
                method: demoForm.current.method,
                body: formdata
            })
                .then(response => {
                    console.log("getProfile", response);
                    if (response.status === 200) alert("Thank you for uploading your demo. We'll get back to you within five days");
                    else alert("Sorry, something went wrong!");
                })
                .catch(e => console.warn(e));
        }
    };

    const onAudioChoosen = () => {
        setAudioFiles([...audioData.current.files]);
        console.log(audioData.current.files[0]);
    };

    return (
        <div>
            <Nav/>
            <div className="Demodrop-Container">
                <h2>DROP DEMO</h2>
                <form method="post" action="/demodrop" onSubmit={doDropDemo} ref={demoForm}>
                    {/* <h3>{props.profile.name}</h3> */}
                    <input type="text" name="name" placeholder=" SONG NAME"/>
                    { !formValidation.nameError ?
                        "" :
                        <Error>{formValidation.name}</Error>}
                    <br/>
                    <textArea className="Input-Demodrop" name="description" placeholder="DESCRIPTION (MIN 20 CHARACTERS)"/>
                    { !formValidation.descriptionError ?
                        "" :
                        <Error>{formValidation.descriptionError && formValidation.description}</Error>}
                    <br/>
                    <div>
                        <label for="files" className="btn">Upload mp3</label>
                        <input  type="file" accept=".mp3"  name="audio" onChange={onAudioChoosen} ref={audioData}/>
                    </div>

                    { !formValidation.audioError ?
                        "" :
                        <Error>{formValidation.audioError && formValidation.audio}</Error>}
                    <div className="DemoBelongToHexagon">
                        <p>All demos are automatically submitted for the HEXAGON record
                            label and Don Diablo's radio show unless otherwise specified
                        </p>
                    </div>
                    <br/>
                    <button className="SubmitButton" type="submit">SUBMIT DEMO</button>
                </form>
            </div>
        </div>
    )

}

export default DemoDrop;