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
        "receiver":"1"
    };
    console.log(data);
    stompClient.send("/app/chat", {}, JSON.stringify(data));
}