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
//自定义校验规则


    $("#sign_in").validate({
                rules:{
                    keyword:{
                        required:true,
                        // checkUsername:true
                    },
                    password:{
                        required:true,
                        rangelength:[6,12]
                    }
                },
                messages:{
                    keyword:{
                        required:"请填写用户名或者邮箱",
                        // checkUsername:"用户名已存在"
                    },
                    password:{
                        required:"密码不能为空",
                        rangelength:"至少输入6个字符,但是不超过12个字符"
                    }

                }

            });

    $("#sign_in").submit(function (){
        var userBO = {
            keyword: $("#keyword").val(),
            password: $("#password").val()
        };
        // alert(userBO.keyword)
        // if (userBO.keyword == null || userBO.keyword == undefined || userBO.keyword == '') {
        //     alert("用户名不能为空");
        //     return;
        // } else if (userBO.password == null || userBO.password == undefined || userBO.password == '') {
        //     alert("密码不能为空");
        //     return;
        // } else if (userBO.password.length < 6) {
        //     alert("密码不能少于6位");
        //     return;
        // }

        // form提交
        $.post("/customer/passport/login",userBO,function (data) {
            if (data.status === "SUCCESS") {
                // document.getElementById("contact_form").reset();
                // var user = data.body;
                // console.log(user);
                toastr.success("登录成功！")
                window.location.href="/index/products";

            }
            else {
                toastr.error(data.msg)
                // alert(data.msg);
                return;
            }
        })
        return false

    })

    $("#get_code").click(function () {
        let count = 60;
        const countDown = setInterval(() => {
            if (count === 0) {
                $("#get_code").text('获取验证码').removeAttr('disabled');
                // $("#get_code").css({
                //     background: '#ff9400',
                //     color: '#fff',
                // });
                clearInterval(countDown);
            } else {
                $("#get_code").attr('disabled', true);
                $("#get_code").css({
                    background: '#d8d8d8',
                    color: '#707070',
                });
                $("#get_code").text('重发验证（'+count + '）');
            }
            count--;
        }, 1000);
        var email = $("#email").val();
        // if (email == null || email == undefined || email == '') {
        //     alert("邮箱不能为空");
        //     return;
        // }
        // console.log(email)
        email = 'email='+$("#email").val();
        $.get("/customer/passport/verification", email, function (data) {
                if (data.status === "SUCCESS") {
                    // document.getElementById("contact_form").reset();
                    // $("#get_code").html("验证已发送").attr("disabled", "disabled");
                    // var user = data.msg;
                    // console.log(user);
                } else {
                    toastr.error(data.msg);
                    return;
                }
            }
        )
    })

    //自定义校验规则
    $.validator.addMethod(
        //规则名称
        "checkPassword",
        //校验的函数
        function(value,element,params){
            //定义一个个标志
            var password = $("#password").val();
            return password==value;
        }
    );

    $("#sign_up").validate({
        rules: {
            username: {
                required: true,
                email: false
            },
            email:{
                required:true,
                email:true
            },
            code:{
                required:true,
                digits:true,
                rangelength:[6,6]
            },
            password: {
                required: true,
                rangelength: [6, 12]
            },
            confirmpassword:{
                required:true,
                checkPassword:true
            }
        },
        messages: {
            username: {
                required: "用户名不能为空",
                email: "用户名不能填写邮箱格式"
            },
            email:{
                required:"邮箱不能为空",
                email:"邮箱格式错误"
            },
            code:{
                required:"填写验证码",
                digits:"验证码为数字",
                rangelength: "验证码长度为6"
            },
            password: {
                required:"密码不能为空",
                rangelength:"至少输入6个字符,但是不超过12个字符"
            },
            confirmpassword:{
                required:"确认密码不能为空",
                checkPassword:"两次输入密码不一致"
            }

        }
    })

    $("#sign_up").submit(function (){
        var userBO = {
            username: $("#username").val(),
            email: $("#email").val(),
            password: $("#password").val(),
            code: $("#code").val()
        };
        // alert(userBO.keyword)
        // if (userBO.username == null || userBO.username == undefined || userBO.username == '') {
        //     alert("用户名不能为空");
        //     return;
        // } else if (userBO.password == null || userBO.password == undefined || userBO.password == '') {
        //     alert("密码不能为空");
        //     return;
        // }
        // else if (userBO.email == null || userBO.email == undefined || userBO.email == '') {
        //     alert("邮箱不能为空");
        //     return;
        // }
        // else if (userBO.code == null || userBO.code == undefined || userBO.code == '') {
        //     alert("验证码不能为空");
        //     return;
        // } else if (userBO.password.length < 6) {
        //     alert("密码不能少于6位");
        //     return;
        // }

        // form提交
        $.post("/customer/passport/regist",userBO,function (data) {
            if (data.status === "SUCCESS") {
                // document.getElementById("contact_form").reset();
                // var user = data.body;
                // console.log(user);
                toastr.success("注册成功，请登录！")
                // window.location.href="/index/login";
            }
            else {
                toastr.error(data.msg);
                return;
            }
        })
        return false
    })

});
