let map;
let layerGroup;
let lastPosition;
let closeVets;
let watchPositionId;

$(document).ready(() => {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(position => {
            lastPosition = position;
            loadMap(position);
        });
    } else {
        $("#map").html("Geolocation is not supported by this browser.");
    }
})

let loadMap = function (position) {
    map = L.map('map').setView([position.coords.latitude, position.coords.longitude], 9);

    L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
        attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, <a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
        maxZoom: 50,
        id: 'mapbox/streets-v11',
        tileSize: 512,
        zoomOffset: -1,
        accessToken: 'pk.eyJ1IjoibWl0a28tMCIsImEiOiJja2k2a2JjM3YwdXloMnVtaHBqNXN1YTJkIn0.Q5JFATs92mguBusbO1zIOw'
    }).addTo(map);

    L.control.cbClose({position: 'topright'}).addTo(map);
    layerGroup = L.layerGroup().addTo(map);

    pinVets();
}

let pinVets = function () {
    layerGroup.clearLayers();

    for (let vet of displayVets) {
        let marker = L.marker([vet.latitude, vet.longitude]).addTo(layerGroup);

        marker.on('click', () => {
            $('#modalReports').modal('show');
            loadVetReports(vet.id);
        });

        marker.bindPopup(`<i>${vet.id}</i>`);

        marker.on('mouseover', e => {
            e.target.openPopup()
        });
    }
}

let loadVetReports = function (vetId) {
    $.ajax({
        url: `/vet/${vetId}/pregled`,
        success: function (data) {
            createReportPreview(data)
        }
    })
}

let createReportPreview = function (data) {
    let tbody = $("#vetData");
    tbody.html("");
    for (let report of data) {
        console.log(report);
        let date = new Date(report.date);

        tbody.prepend("<tr></tr>");
        tbody.children(":first").append(`<td>${report.title}</td>`);
        tbody.children(":first").append(`<td>${date.toLocaleString()}</td>`);
        tbody.children(":first").append(`<td><div class="form-check text-center"><input class="form-check-input" type="checkbox"  disabled ${report.done && 'checked'}></div></td>`);
        tbody.children(":first").append(`<td><a href="/prijava/${report.id}">Преглед</a></td>`);
    }
}

let toggleCloseVets = function (e) {
    if (e.target.checked) {
        watchPositionId = navigator.geolocation.watchPosition(position => {
            lastPosition = position;
            loadCloseVets();
        });
    } else {
        navigator.geolocation.clearWatch(watchPositionId);
        displayVets = vets;
        pinVets();
    }
}

let loadCloseVets = function () {
    $.ajax({
        url: "/vet/bliski",
        data: `lat=${lastPosition.coords.latitude}&lon=${lastPosition.coords.longitude}`,
        success: function (data) {
            closeVets = data;
            displayVets = closeVets;
            pinVets();
        }
    })
}

L.Control.CheckboxClose = L.Control.extend({
    onAdd: function (map) {
        let checkboxLabel = L.DomUtil.create('label');
        let child = L.DomUtil.create('input');

        checkboxLabel.innerText = "Блиски ветеринарни ";
        checkboxLabel.id = "vetClose";
        checkboxLabel.appendChild(child);
        child.type = 'checkbox';
        child.onclick = toggleCloseVets;

        return checkboxLabel;
    },

    onRemove: function (map) {
    }
});

L.control.cbClose = function (opts) {
    return new L.Control.CheckboxClose(opts);
}