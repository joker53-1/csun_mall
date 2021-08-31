$(function () {
    $("#serial_form").submit(function () {
        let values = $("#serial_form").serialize();
        $("#serial_submit").html("Send...").attr("disabled", "disabled")
        $.post("/serial", values, function (data) {
            alert(data.msg)
            $("#captcha_img").attr("src", "/captcha")
            $("#serial_submit").html("Submit").removeAttr("disabled");
            if (data.code === "success") {
                document.getElementById("serial_form").reset();
            }
        })
        return false
    })

    $("#contact_form").submit(function () {
        let values = $("#contact_form").serialize();
        $("#contact_submit").html("Send...").attr("disabled", "disabled")
        $.post("/contact", values, function (data) {
            $("#contact_submit").html("Submit").removeAttr("disabled");
            alert(data.msg)
            if (data.code === "success") {
                document.getElementById("contact_form").reset();
            }
        })
        return false
    })

    $("#products_request").submit(function () {
        let values = $("#products_request").serialize();
        $("#products_request_submit").html("Send...").attr("disabled", "disabled")
        $.post("/contact", values, function (data) {
            $("#products_request_submit").html("Submit").removeAttr("disabled");
            alert(data.msg)
            if (data.code === "success") {
                document.getElementById("products_request").reset();
            }
        })
        return false
    })

    $(".product_otherpic_item").click(function () {
        $(".product_otherpic_item").addClass("special4")
        $(this).removeClass("special4");
        let tmp = $(this).attr("src")
        $("#mainPic").attr("src", tmp)
    })

    $("#pre_new_a").click(function () {
        if (!$(this).attr("jump")) {
            event.preventDefault()
        }
    })

    $("#next_new_a").click(function () {
        if (!$(this).attr("jump")) {
            event.preventDefault()
        }
    })

    $(".has_sub_menu").hover(function () {
        $(this).find(".sub_menu").show();
    }, function () {
        $(this).find(".sub_menu").hide();
    })


    $("#lang_select").change(function () {
        location.href = $(this).val()

    })


    $("#captcha_img").click(function () {
        $(this).attr("src", "/captcha?tm=" + new Date().getTime())
    })


    $.ScrollFixed = function (el, options) {
        var base = this;
        base.$el = $(el);
        base.el = el;
        var target = base.$el;
        var original_left = parseInt(target.css('left'));
        var original_right = parseInt(target.css('right'));

        var _fix_position = function () {
            if (base.options.fixed == 'right') {
                target.css('right', ($(window).scrollLeft() + $(window).width() - $(document).width() + original_right) + 'px');
            } else if (base.options.fixed == 'left') {
                target.css('left', (original_left - $(window).scrollLeft()) + 'px');
            }
        };

        var windowResize = function () {
            _fix_position();
        };

        var windowScroll = function () {
            _fix_position();
        };

        base.init = function () {
            base.options = $.extend({}, $.ScrollFixed.defaultOptions, options);
            $(window).resize(windowResize);
            $(window).scroll(windowScroll);
            _fix_position();
            console.log(base.options.fixed);
        };

        base.init();
    };

    $.ScrollFixed.defaultOptions = {
        fixed: 'left'
    };

    $.fn.scrollFixed = function (options) {
        return this.each(function () {
            (new $.ScrollFixed(this, options));
        });
    };

    $('.Top-header').scrollFixed({fixed: 'left'});

})