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

function getCookie(name){
    var strcookie = document.cookie;//获取cookie字符串
    var arrcookie = strcookie.split("; ");//分割
    //遍历匹配
    for ( var i = 0; i < arrcookie.length; i++) {
        var arr = arrcookie[i].split("=");
        if (arr[0] == name){
            return arr[1];
        }
    }
    return "";
}


function getData(data) {
    var obj = JSON.parse(data);
    // codeMapping(obj);
    return obj;
}
