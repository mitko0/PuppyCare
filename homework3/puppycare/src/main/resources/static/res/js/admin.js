let selectedUsers = [];
let selectedVet;

$(document).ready(() => {
    let searchUser = $("#searchUser");
    let searchVet = $("#searchVet");

    searchUser.autocomplete({
        source: users.map(user => user.username),
        select: function(event, ui) {
            selectedUsers.indexOf(ui.item.label) === -1 && selectedUsers.push(ui.item.label);
            showItems(selectedUsers, "#selectedUsers", 'remUser', 'users');

            $(this).val('');
            return false;
        }
    });

    searchVet.autocomplete({
        source: vets.map(vet => vet.id + ''),
        select: function(event, ui) {
            selectedVet = ui.item.label;
            showItems([selectedVet], "#selectedVets", 'remVet', 'vet');

            $(this).val('');
            return false;
        }
    });

})

$(document).on('click', '.remUser', e => {
    selectedUsers = selectedUsers.filter(user => user !== e.target.value);
    showItems(selectedUsers, "#selectedUsers", 'remUser', 'users');
});

$(document).on('click', '.remVet', () => {
    selectedVet = null;
    let data = selectedVet ? selectedUsers : [];
    showItems(data, "#selectedVets", 'remVet', 'vet');
});

let showItems = function (data, target, cls, name) {
    let elem = $(target);
    elem.html("");

    data.forEach(item => {
        elem.append(`<input class="form-control rem ${cls}" type="text" value="${item}" readOnly name="${name}">`)
    })
}
