$(document).ready(function() {
    var mapContainer = $('#map');
    var sensors = $('.sensor-module');

    var map = new google.maps.Map(mapContainer.get(0), {
        zoom: 12,
        center: new google.maps.LatLng(sensors.first().data('lat'), sensors.first().data('lon')),
        scrollwheel: false
    });

    sensors.each(function() {
        var sensor = $(this);

        var marker = new google.maps.Marker({
            position: new google.maps.LatLng(sensor.data('lat'), sensors.data('lon')),
            map: map,
            label: 1 + sensor.data('number').toString()
        });

    });

});