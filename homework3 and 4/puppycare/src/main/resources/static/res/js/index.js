$(document).ready(() => {
    $("#reportSubmit").click(e => {
        e.preventDefault();
        $("#reportModal").modal('hide');

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(submitForm);
        }
    });
});

let submitForm = function (position) {
    let reportForm = $("#reportForm");

    let data = reportForm.serialize() + `&lat=${position.coords.latitude}&lon=${position.coords.longitude}`;

    reportForm[0].reset();

    $.ajax({
        method: "POST",
        url: "/prijava",
        data: data,
        success: function (data) {
            let classes = "alert alert-dismissible fade show ";
            let msg;

            if (data) {
                msg = "Пораката е успешно испратена!";
                classes += "alert-success";
            } else {
                msg = "Настана грешка при испраќање на пораката!";
                classes += "alert-danger";
            }

            let alert = `<div class='${classes}' role="alert">
                ${msg}
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>`
            $('#response').html(alert);
        }
    })
}