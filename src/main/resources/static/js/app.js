$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $(".has_sub_menu").hover(function () {
        $(this).find(".sub_menu").show();
    }, function () {
        $(this).find(".sub_menu").hide();
    });

    $("#contact_form").submit(function () {
        let values = $("#contact_form").serialize();
        $("#contact_submit").html("Send...").attr("disabled", "disabled")
        $.post("/contact/contactUs", values, function (data) {
            $("#contact_submit").html("Submit").removeAttr("disabled");
            alert(data.body)
            if (data.status === "SUCCESS") {
                document.getElementById("contact_form").reset();
            }
        })
        return false
    })

    $("#sign_in").submit(function (){
        var userBO = {
            keyword: $("#keyword").val(),
            password: $("#password").val()
        };
        // alert(userBO.keyword)
        if (userBO.keyword == null || userBO.keyword == undefined || userBO.keyword == '') {
            alert("用户名不能为空");
            return;
        } else if (userBO.password == null || userBO.password == undefined || userBO.password == '') {
            alert("密码不能为空");
            return;
        } else if (userBO.password.length < 6) {
            alert("密码不能少于6位");
            return;
        }

        // form提交
        $.post("/customer/passport/login",userBO,function (data) {
            if (data.status === "SUCCESS") {
                // document.getElementById("contact_form").reset();
                var user = data.body;
                console.log(user);
                window.location.href="/index/products";
            }
            else {
                console.log(data.body);
                return;
            }
        })
        return false

    })

    $("#get_code").click(function () {
        var email = $("#email").val();
        if (email == null || email == undefined || email == '') {
            alert("邮箱不能为空");
            return;
        }
        // console.log(email)
        email = 'email='+$("#email").val();
        $.get("/customer/passport/verification", email, function (data) {
                if (data.status === "SUCCESS") {
                    // document.getElementById("contact_form").reset();
                    $("#get_code").html("验证已发送").attr("disabled", "disabled");
                    var user = data.msg;
                    console.log(user);
                } else {
                    alert(data.msg);
                    return;
                }
            }
        )
    })

    $("#sign_up").submit(function (){
        var userBO = {
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            code: $("#code").val()
        };
        // alert(userBO.keyword)
        if (userBO.username == null || userBO.username == undefined || userBO.username == '') {
            alert("用户名不能为空");
            return;
        } else if (userBO.password == null || userBO.password == undefined || userBO.password == '') {
            alert("密码不能为空");
            return;
        }
        else if (userBO.email == null || userBO.email == undefined || userBO.email == '') {
            alert("邮箱不能为空");
            return;
        }
        else if (userBO.code == null || userBO.code == undefined || userBO.code == '') {
            alert("验证码不能为空");
            return;
        } else if (userBO.password.length < 6) {
            alert("密码不能少于6位");
            return;
        }

        // form提交
        $.post("/customer/passport/regist",userBO,function (data) {
            if (data.status === "SUCCESS") {
                // document.getElementById("contact_form").reset();
                var user = data.body;
                console.log(user);
                alert("注册成功，请登录！")
                // window.location.href="/index/login";
            }
            else {
                alert(data.msg);
                return;
            }
        })
        return false
    })
});
