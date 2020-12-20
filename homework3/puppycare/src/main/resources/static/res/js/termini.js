$(document).ready( function () {
    $('#terms').DataTable();

    $(".submit").click(e => {
        let target = $(e.currentTarget);

        submitData({
            ownerId: target.attr("user"),
            petId: target.attr("pet"),
            accept: target.attr("accept")
        });

        target.parent().parent().remove();
    })
});

let submitData = function (data) {
    $.ajax({
        method: "POST",
        url: `/vet/termini`,
        data: data,
    })
}