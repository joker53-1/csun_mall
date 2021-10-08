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
});
