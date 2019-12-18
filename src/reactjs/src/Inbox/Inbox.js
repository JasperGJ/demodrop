import React, {useState, useEffect} from 'react';
import './Inbox.css';

const Inbox = () => {
    const [hightlightedItem, setHightlight] = useState(-1);

    const [selectedDemo, setSelectedDemo] = useState(-1);
    const [comment, setComments] = useState("");
    const [status, setStatus] = useState("");
    const [name, setName] = useState("");

    const [templates, setTemplates] = useState([]);
    const [opts, setOpts] = useState([]);
    const [inbox, setInbox] = useState({
        loaded: false,
        data: {}
    });
    const [demo, setDemo] = useState({
        loaded: false,
        demo: {}
    });


    useEffect(
        () => {
            getInbox();
            gettemplates();
        }, [],
    );

    const handleChange = (event) => {
        setComments(event.target.value);
    };

    const select = (event) => {
        setComments("Hello dear "+ name + event.target.value);
    };

    const accept = () => {
        setStatus("ACCEPT");
        const optsArray =
            templates.filter(template => template.status === "ACCEPT");
        setOpts(optsArray.map((template, index) => <option key={index}>{template.comment}</option>));
        setComments("Hello dear "+ name+ optsArray[0].comment);
    };

    const decline = () => {
        setStatus("DECLINE");
        const optsArray =
            templates.filter(template => template.status === "DECLINE");
        setOpts(optsArray.map((template, index) => <option key={index}>{template.comment}</option>));
        const name = inbox.data.items[selectedDemo].artist;
        setComments("Hello dear "+ name+  optsArray[0].comment);
    };

    const start = () => {
        fetch("/review/" + selectedDemo)
            .then(responce => {
                console.log("responce", responce);
                if (responce.status === 200) alert("De demo is set to review");
            })
            .catch(e => console.warn(e));
    };

    const getInbox = () => {
        fetch("/inbox")
            .then(responce => {
                console.log("responce", responce);
                if (responce.status === 200) return responce.json();
            })
            .then(json => setInbox({loaded: true, data: json}))
            .catch(e => console.warn(e));
    };

    const gettemplates = () => {
        fetch("/templates")
            .then(responce => {
                if (responce.status === 200) return responce.json();
            })
            .then(json => setTemplates([...json]))
            .catch(e => console.warn(e));
    };

    const getDemo = (id) => {
        fetch("/demo/" + id)
            .then(responce => {
                console.log("responce", responce);
                if (responce.status === 200) return responce.json();
            })
            .then(json => {
                const blob = new Blob([json.audio], {type: 'audio/mp3'});
                console.log("audioblob", blob);
                setDemo({loaded: true, demo: json, audio: blob})
            })
            .catch(e => console.warn(e));
    };

    const setComment = () => {
        const data = new FormData();
        data.append("status", status);
        data.append("comment", comment);

        fetch("/review/" + selectedDemo, {
            method: "POST",
            body: data
        }).then(response => {
            console.log("response", response);
            if (response.status === 200) {
                alert("comment is sent");
            }
        }).catch(e => console.warn(e))

    };

    const highlightItem = (index) => {
        setHightlight(index);
    };

    const itemClicked = (id,index) => {
        setSelectedDemo(id);
        console.log(index,inbox.data.items);
        setName(inbox.data.items[index].artist);
        getDemo(id);
    };

    return (


        // css van ComponentContainerInbox staat in App.css
        <div className="ComponentContainerInbox">
            <div className="InboxContainer">
                <div className="BoxDemos">
                    <h2 className="InboxH2">INBOX DEMOS</h2>
                    <div className="DemoInfo">
                        {inbox.loaded
                            ?
                            inbox.data.items.map((item,index) =>
                                <div key={item.id}
                                     onMouseLeave={() => highlightItem(-1)}
                                     onMouseOver={() => highlightItem(item.id)}
                                     onClick={() => itemClicked(item.id,index)}
                                     className={
                                         selectedDemo === item.id
                                             ? "Demo Selected"
                                             : hightlightedItem === item.id
                                             ? "Demo Highlight"
                                             : "Demo"
                                     }>

                                    <div className="PhotoArtistDate">
                                        <div className="Thumbnail"><img className="HexagonThumbnail"
                                                                        src={`data:image/png;base64,${item.thumbnail}`}
                                                                        alt="profile "/></div>
                                        <div className="Artist">Artist: {item.artist}</div>
                                        <div className="Date">{new Date(item.uploaded).toLocaleDateString()}</div>
                                    </div>
                                    <div className="StatusTitleSong">
                                        <div className="Status">Status: {item.status}</div>
                                         <div className="TitleSong">Title: {item.title}</div>
                                    </div>
                                </div>
                            )
                            :
                            <div>ophalen</div>
                        }
                    </div>
                </div>

                <div className="DemoShow">
                    <h2 className="InboxH2">DEMO</h2>
                    <div className="DemoInfo">
                        <h5>{demo.loaded
                            ? demo.demo["artist"] + " - " + demo.demo.title
                            : ""}
                        </h5>
                        <audio src={demo.loaded ? `data:audio/mp3;base64,${demo.demo.audio}` : ""} controls/>
                        <br/>
                        <button className="StartButton" onClick={start}>Start Review</button>
                        <br/>
                        <button className="AcceptButton" onClick={accept}>Accept</button>
                        <button className="DeclineButton" onClick={decline}>Decline</button>
                        <div className="DescriptionContainer">
                            <span className="Description">Description</span>
                            <textarea value={demo.loaded ? demo.demo.description : "niks"}
                                      className="TextAreaDescription"/>
                        </div>
                        <div className="AnswerContainer">
                            <div id="AnswerAndSelectMessageContainer">
                                <span>Answer</span>
                                <select id="SelectMessage" onChange={select}>
                                    {opts}
                                </select>
                            </div>

                            <textarea className="TextAreaAnswer" value={comment} onChange={handleChange}/>
                        </div>
                        <button className="SubmitButton" onClick={setComment}>SEND</button>
                    </div>
                </div>

            </div>
        </div>
    )
};

export default Inbox;