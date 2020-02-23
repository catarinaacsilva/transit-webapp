mapboxgl.accessToken = 'pk.eyJ1IjoiZGlhc2R1YXJ0ZSIsImEiOiJjazZ2YjZmaTYwMDJjM3JzNHZvajJhdTlyIn0.CQS2LCyFIVKZqDuzW_qQmA';

// store current markers
let currentMarkers=[];

// store types names
let  types_names = ['NA', 'Accident', 'Congestion', 'Disabled Vehicle', 'Mass Transit', 'Miscellaneous', 'Other News',
'Planned Event', 'Road Hazard', 'Construction', 'Alert', 'Weather'];

let map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/streets-v11',
	center: [-8.65362, 41.6412], // starting position
	zoom: 13 // starting zoom
});

// Add zoom and rotation controls to the map.
map.addControl(new mapboxgl.NavigationControl());

if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(function (position) {
		lat = position.coords.latitude;
		lng = position.coords.longitude;
		let newLatLng = new mapboxgl.LngLat(lng, lat);
		console.log(newLatLng);
		map.panTo(newLatLng);
	});
}

map.on("moveend", function(){
	let bounds = map.getBounds();
	let sw = bounds.getSouthWest();
	let ne = bounds.getNorthEast();
	let url = 'incidents?lat0='+sw.lat+'&lon0='+sw.lng+'&lat1='+ne.lat+'&lon1='+ne.lng;

	fetch(url).then(res => res.json()).then((out) => {
		// remove markers
        if (currentMarkers !== null) {
            for (var i = currentMarkers.length - 1; i >= 0; i--) {
              currentMarkers[i].remove();
            }
        }

		for(var i=0; i<out.length; i++){
		    let newLatLng = new mapboxgl.LngLat(out[i]["point"]["lon"], out[i]["point"]["lat"]);
			let desc = out[i]["description"];
			let type = ("0" + out[i]["type"]).slice(-2);

			// create a HTML element for each feature
			var el = document.createElement('div');
            el.className = 'marker'+type;

            // make a marker for each feature and add to the map
            let oneMarker = new mapboxgl.Marker(el);
            oneMarker.setLngLat(newLatLng);
            oneMarker.setPopup(new mapboxgl.Popup({ offset: 25 }).setHTML('<h3>' + types_names[out[i]["type"]] + '</h3><p><h4>'+desc+'</p></h4>'));
            oneMarker.addTo(map);

            // save the marker into currentMarkers
            currentMarkers.push(oneMarker);
		}
	}).catch(err => { throw err });
});