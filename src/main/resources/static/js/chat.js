const chat = {
    // 聊天组件显示
    chatBox: true,
    // 聊天登录显示
    chatLogin: true,
    // 聊天内容显示
    chatMain: false
};
$(function () {
    Object.defineProperties(chat, {
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
    if (!chat.chatBox) {

        openChat()
    } else {
        closeChat()
    }
}

function openChat() {
    chat.chatBox = true
}

function closeChat() {
    chat.chatBox = false
}

function loginChat() {
    connect();
    chat.chatLogin = false
    chat.chatMain = true
}

// (function () {
//     var socket = new SockJS('/messages');
//     var stompClient = Stomp.over(socket);
//     stompClient.connect(
//         {},
//         function connectCallback(frame) {
//             console.log("************链接成功**********")
//             // debugger
//             stompClient.subscribe('/topic/subscribe', function (greeting) {
//                 // debugger
//                 console.log(greeting.body)
//                 showMessage(greeting.body)
//                 // showGreeting(greeting.body);
//             });
//             // debugger
//             stompClient.send('/app/marco',{principal:'test'},"Hellow")
//         },
//         function errorCallBack(error) {
//             console.log("************链接**********失败**********")
//         }
//     );
//
// })();
//
function showMessage(data){
    console.log(data);
    $("#show_content_admin").append(data.message);
}

function getData(data) {
    var obj = JSON.parse(data);
    // codeMapping(obj);
    return obj;
}
