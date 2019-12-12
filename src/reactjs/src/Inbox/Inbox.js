import React, {useState,useEffect} from 'react';
import './Inbox.css';


const Inbox = () => {

    const [inbox,setInbox] = useState({
        loaded : false,
        data : {}
    });

    useEffect(
        () => {
            getInbox();
        },
        [],
    );

    const getInbox = () => {

        fetch("/inbox")



            .then(responce => {
                console.log("responce",responce);
                if (responce.status === 200)return responce.json();
            })
            .then(json => setInbox({loaded : true, data : json}))
            .catch(e => console.warn(e));
    }

    console.log("inbox",inbox)
    return (
        <div>
            <div className="InboxContainer">
                <div className="BoxDemos">
                    <h2 className="InboxH2">INBOX DEMOS</h2>
                    {inbox.loaded
                    ?
                        inbox.data.items.map((item,index)  =>
                            <div key={index} className="Demo">
                                <div className="Date">28-9-2019</div>
                                {/*//todo de radio button moet nog gemaakt worden. probleem is dat als ik hem aanmaak, hij telkens centreert*/}
                                <div className="TitleSong">{item.title}</div>
                            </div>
                        )
                    :
                        <div>ophalen</div>
                    }

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
                        <button>SEND</button>
                    </div>
                </div>

            </div>
        </div>
    )
};

export default Inbox;