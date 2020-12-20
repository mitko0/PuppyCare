$(document).ready( function () {
    $('#reports').DataTable();

    $("#createPet").click(e => {
        e.preventDefault();
        $("#newPetModal").modal('hide');

        submitForm();
    })
});

let submitForm = function () {
    let petForm = $("#createPetForm");

    let data = new FormData(petForm[0]);

    petForm[0].reset();

    $.ajax({
        method: "POST",
        url: "/milenici/novo",
        data: data,
        contentType: false,
        processData: false,
        success: function (data) {
            let classes = "alert alert-dismissible fade show ";
            let msg;

            if (data) {
                msg = "Ново милениче е креирано!";
                classes += "alert-success";
            } else {
                msg = "Настана грешка при креирање на ново милениче!";
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