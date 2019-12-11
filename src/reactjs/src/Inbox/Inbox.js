import React from 'react';
import './Inbox.css';
import Button from "../Components/Button/Button";


const Inbox = () => {
    return (
        <div>
            <div className="InboxContainer">
                <div className="BoxDemos">
                    <h2 className="InboxH2">INBOX DEMOS</h2>
                    <div className="Demo">
                        <div className="Date">28-9-2019</div>
                        {/*//todo de radio button moet nog gemaakt worden. probleem is dat als ik hem aanmaak, hij telkens centreert*/}
                        <div className="TitleSong">Title of the Song</div>
                    </div>
                </div>

                <div className="DemoShow">
                    <h2 className="InboxH2">DEMO</h2>
                    <div className="DemoInfo">
                        <h5>DJ Jake Peralta - New Song</h5>
                        <audio controls/>
                        <br/>
                        <button className="AcceptButton">Accept</button>
                        <button className="DeclineButton">Decline</button>
                        <div className="DescriptionContainer">
                            <span className="Description">Description</span>
                            <textarea className="TextAreaDescription"/>
                        </div>
                        <div className="AnswerContainer">
                            <div id="AnswerAndSelectMessageContainer">
                                <span>Answer</span>
                                <select id="SelectMessage">
                                    <option value="Sorry">Sorry etc</option>
                                    <option value="Hoera">Hoera etc</option>
                                </select>
                            </div>

                            <textarea className="TextAreaAnswer"/>
                        </div>
                        <Button>SEND</Button>
                    </div>
                </div>

            </div>
        </div>
    )
};

export default Inbox;