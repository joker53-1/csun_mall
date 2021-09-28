var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}
var uid = 2;
function connect() {
    var socket = new SockJS('/messages');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        // stompClient.subscribe('/topic/subscribe', function (greeting) {
        //     // debugger
        //     console.log(greeting.body)
        //     showMessage(greeting.body)
        //     // showGreeting(greeting.body);
        // });
        stompClient.subscribe('/user/' + uid + '/chat', function (data) {
            showMessage(getData(data.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

function sendName() {
    var data = {
        "message": $("#question").val(),
        "receiver":"1",
        "type":1
        // "deviceId":getCookie("device_id")
    };
    console.log(data);
    stompClient.send("/app/chat", {}, JSON.stringify(data));
    document.getElementById("question").value = "";
    var span = '<div class="chat_message_box chat_right_box">\n' +
        '                        <div class="chat_box_head">\n' +
        '                                <span>\n' +
        '                                    客服\n' +
        '                                </span>\n' +
        '                            <span>\n' +
        '                                    下午04:46\n' +
        '                                </span>\n' +
        '                        </div>\n' +
        '                        <div class="chat_message">\n' +
        '\n' +data.message+
        '                        </div>\n' +
        '                    </div>'
    $("#show_content_admin").append(span);
}

function showMessage(data){
    console.log(data);
    var span = '<div class="chat_message_box chat_left_box">\n' +
        '                        <div class="chat_box_head">\n' +
        '                                <span>\n' +
        '                                    用户1\n' +
        '                                </span>\n' +
        '                            <span>\n' +
        '                                    下午04:46\n' +
        '                                </span>\n' +
        '                        </div>\n' +
        '                        <div class="chat_message">\n' +
        '\n' +data.message+
        '                        </div>\n' +
        '                    </div>'
    $("#show_content_admin").append(span);

}