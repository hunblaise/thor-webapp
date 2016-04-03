$(document).ready(function() {
    var mapContainer = $('#map');

    var location = new google.maps.LatLng(mapContainer.data('lat'), mapContainer.data('lon'));

    var map = new google.maps.Map(mapContainer.get(0), {
        zoom: 12,
        center: location,
        scrollwheel: false
    });

    var marker = new google.maps.Marker({
        position: location,
        map: map
    });
});