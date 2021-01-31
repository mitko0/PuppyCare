$(document).ready(() => {
    $("#datepicker").datepicker({
        minDate: new Date(),
        firstDay: 1,
        dayNames: ["Недела", "Понеделник", "Вторник", "Среда", "Четврток", "Петок", "Сабота"],
        dayNamesMin: ["Нед", "Пон", "Вто", "Сре", "Чет", "Пет", "Саб",],
        dayNamesShort: ["Нед", "Пон", "Вто", "Сре", "Чет", "Пет", "Саб",],
        monthNames: ["Јануари", "Фебруари", "Март", "Април", "Мај", "Јуни", "Јули", "Август", "Септември", "Октомври", "Ноември", "Декември"],
        monthNamesShort: ["Јан", "Феб", "Мар", "Апр", "Мај", "Јун", "Јул", "Авг", "Сеп", "Окт", "Ное", "Дек"],
    });

    $("#termSubmit").click(e => {
        e.preventDefault();
        $("#petTermModal").modal('hide');

        submitForm();
    });
});

let submitForm = function () {
    let petTermForm = $("#petTermForm");
    let data = petTermForm.serialize();

    petTermForm[0].reset();

    $.ajax({
        method: "POST",
        url: `/milenici/${pet.id}/termin`,
        data: data,
        success: function (data) {
            let classes = "alert alert-dismissible fade show ";
            let msg;

            if (data) {
                msg = "Терминот е закажан!";
                classes += "alert-success";
            } else {
                msg = "Настана грешка при закажување нов термин!";
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