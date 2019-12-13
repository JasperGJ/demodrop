import React, {useState,useEffect} from 'react';
import './Inbox.css';


const Inbox = () => {

    const [hightlightedItem,setHightlight] = useState(-1);
    const [templates,setTemplates] = useState([]);
    const [inbox,setInbox] = useState({
        loaded : false,
        data : {}
    });
    const [demo,setDemo] = useState({
        loaded : false,
        demo : {}
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

    const gettemplates = () => {
        fetch("/templates")
            .then(responce => {
                console.log("responce",responce);
                if (responce.status === 200)return responce.json();
            })
            .then(json => setTemplates( json))
            .catch(e => console.warn(e));
    }

    const getDemo = (id) => {
        fetch("/demo/"+id)
            .then(responce => {
                console.log("responce",responce);
                if (responce.status === 200)return responce.json();
            })
            .then(json => {
                var blob = new Blob([json.audio], {type: 'audio/mp3'});
                console.log("audioblob",blob);
                setDemo({loaded : true, demo : json, audio : blob})
            })
            .catch(e => console.warn(e));
    }

    const highlightItem = (index) => {
        setHightlight(index);
    }

    const itemClicked = (index) => {
        getDemo(index);
    }

    console.log("demo",demo);

    return (
        <div>
            <div className="InboxContainer">
                <div className="BoxDemos">
                    <h2 className="InboxH2">INBOX DEMOS</h2>
                    {inbox.loaded
                    ?
                        inbox.data.items.map((item,index)  =>
                            <div key={item.id} onMouseLeave={() => highlightItem(-1)} onMouseOver={() => highlightItem(item.id)} onClick={() => itemClicked(item.id)} className={`Demo ${hightlightedItem === item.id ? "Highlight" : ""}`}>
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
                        <h5>{demo.loaded ? demo.demo.artist+" - "+demo.demo.title: "niks"}</h5>
                        <audio src={demo.loaded ? `data:audio/mp3;base64,${demo.demo.audio}` : ""} type="audio/mp3" controls/>
                        <br/>
                        <button className="AcceptButton">Accept</button>
                        <button className="DeclineButton">Decline</button>
                        <div className="DescriptionContainer">
                            <span className="Description">Description</span>
                            <textarea  value={demo.loaded ? demo.demo.description : "niks"} className="TextAreaDescription" />
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