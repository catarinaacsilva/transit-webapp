mapboxgl.accessToken = 'pk.eyJ1IjoiZGlhc2R1YXJ0ZSIsImEiOiJjazZ2YjZmaTYwMDJjM3JzNHZvajJhdTlyIn0.CQS2LCyFIVKZqDuzW_qQmA';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
    center: [-8.65362, 40.6412], // starting position
    zoom: 13 // starting zoom
});

// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl());