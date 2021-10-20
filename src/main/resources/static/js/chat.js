const chatProp = {
    // 聊天组件显示
    chatBox: true,
    // 聊天登录显示
    chatLogin: true,
    // 聊天内容显示
    chatMain: false
};
$(function () {
    Object.defineProperties(chatProp, {
        'chatBox': {
            configurable: true,
            set: function (newValue) {
                chatBox = newValue;
                if (newValue) {
                    document.getElementById("chatBox").style.display = "block"
                } else {
                    document.getElementById("chatBox").style.display = "none"
                }
            },
            get: function () {
                return chatBox;
            },
        },
        'chatLogin': {
            configurable: true,
            set: function (newValue) {
                if (newValue) {
                    document.getElementById("chatLogin").style.display = "flex"
                } else {
                    document.getElementById("chatLogin").style.display = "none"
                }
            },
            get: function () {
                return this;
            },
        },
        'chatMain': {
            configurable: true,
            set: function (newValue) {
                if (newValue) {
                    document.getElementById("chatMain").style.display = "flex"
                } else {
                    document.getElementById("chatMain").style.display = "none"
                }
            },
            get: function () {
                return this;
            },
        },
    })
})


function clickChat() {
    let chatBox = chatProp.chatBox === false || chatProp.chatBox === true ? chatProp.chatBox : false
    console.log(chatBox)
    if (!chatBox) {
        openChat()
    } else {
        closeChat()
    }
}

function openChat() {
    chatProp.chatBox = true
    if (getMessageId() === '') {
        chatProp.chatLogin = true
        chatProp.chatMain = false
    } else {
        if (stompClient === null) {
            connect()
            renderChat()
            chatProp.chatLogin = false
            chatProp.chatMain = true
        }
        else {
            renderChat()
            chatProp.chatLogin = false
            chatProp.chatMain = true
        }
    }
}

function closeChat() {
    chatProp.chatBox = false
    chatProp.chatLogin = false
    chatProp.chatMain = false
}

function loginChat() {
    let chatFrom = $("#chat_from");
    $.post("/message/commit", chatFrom.serialize(), function (res) {
        if (res.status === "SUCCESS") {
            setMessageId(res.body)
            connect();
            welcome();
            chatProp.chatLogin = false
            chatProp.chatMain = true
            return false;
        } else {
            alert("开启聊天失败")
        }
    })
}

var stompClient = null;

function connect() {
    var socket = new SockJS('/messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        let messageId = localStorage.getItem("messageId");
        stompClient.subscribe('/user/' + messageId + '/chat', function (data) {
            showMessage(JSON.parse(data.body));
        });
    });

}


function sendMessage(that) {
    let message = $("#question")
    if(message.val()== null || message.val() == undefined || message.val() == '')
        return;
    $(that).html("发送中...").attr("disabled", "disabled")
    var data = {
        "messageId": getMessageId(),
        "message": message.val(),
        "type": 0,
        "sendTime": new Date(),
    };
    stompClient.send("/app/chat", {}, JSON.stringify(data));
    showMessage(data)
    message.val("")
    $(that).html("发送").removeAttr("disabled")
}


function showMessage(messageInfo) {
    console.log(messageInfo)
    let span = '';
    let user = localStorage.getItem("user");
    // console.log(user_token)
    let user_data=""
    if (user == null || user == undefined || user == '')
        user_data ="用户";
    else
        user_data=JSON.parse(user).username

    if (messageInfo.type) {
        span = `<div class="chat_message_box chat_left_box" >
                    <div class="chat_box_head">
                        <span>
                            ${messageInfo.replyUserName}
                        </span>
                        <span>
                        ${simpleTime(messageInfo.sendTime)}
                        </span>
                    </div>
                    <div class="chat_message" >
                        ${messageInfo.message}
                    </div>
                </div>`;
    } else {
        span = `<div class="chat_message_box chat_right_box">
                    <div class="chat_box_head">
                        <span>
                        ${user_data}
                        </span>
                        <span>
                           ${simpleTime(messageInfo.sendTime)}
                        </span>
                    </div>
                    <div class="chat_message">
                        ${messageInfo.message}
                    </div>
                </div>`;
    }
    $("#show_content_admin").append(span);


}

function welcome(){

    span = `<div class="chat_message_box chat_left_box">
    <div class="chat_box_head">
                                <span th:text="#{co_customer_ser}">
                                    客服
                                </span>
        <span id="open_time">${simpleTime(new Date())}
                                </span>
    </div>
    <div class="chat_message">
        您好，欢迎您访问人工客服，请问有什么可以帮您
    </div>
</div>`;
    $("#show_content_admin").append(span);
}

$("#open_time").append(simpleTime(new Date()))
function headerMenuClose() {
    $("#Open").hide()
}

function cartMenuClose(){
    $("#shoppingCart").hide()
}

function getMessageId() {
    let messageId = localStorage.getItem("messageId");
    if (messageId === undefined || messageId === null) {
        messageId = ''
    }
    return messageId;
}

function setMessageId(messageId) {
    localStorage.setItem("messageId", messageId);
}
function slideBottom(){
    // $("#show_content_admin").scrollTo(0,$("#show_content_admin").scrollHeight);
}


function renderChat() {
    $.get("/message/page/" + getMessageId(), function (res) {
        let dataList = res.body.dataList;
        for (let index in dataList) {
            showMessage(dataList[index])
        }
    })
    slideBottom()
}

function simpleTime(time) {
    let today = new Date();
    let date;
    if (time instanceof Date) {
        date = time
    } else {
        date = new Date(time);
    }
    let year = date.getFullYear()
    let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : (date.getMonth() + 1)
    let day = date.getDay()< 10 ? "0" + date.getDay() : date.getDay()
    let hour = date.getHours()< 10 ? "0" + date.getHours() : date.getHours()
    let minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes()
    if( today.getFullYear() === year ){
        if(today.getMonth() === date.getMonth() && today.getDay() === date.getDay()){
            return `&nbsp;&nbsp;&nbsp;${hour}:${minutes}&nbsp;`
        }else{
            return `&nbsp;&nbsp;&nbsp;${month}-${day} ${hour}:${minutes}&nbsp;`
        }
    }else{
        return `&nbsp;&nbsp;&nbsp;${year}-${month}-${day} ${hour}:${minutes}&nbsp;`
    }


}