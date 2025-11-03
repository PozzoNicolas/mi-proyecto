navigator.permissions.query({name:'geolocation'})
  .then(r => console.log("Permission state:", r.state));

const mapa = L.map('mi_mapa').setView([-34.6701103, -58.5628770], 10);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(mapa);

L.marker([-34.6701103, -58.5628770]).addTo(mapa).bindPopup("UNLaM");
L.marker([-34.6407308, -58.4804502]).addTo(mapa).bindPopup("Veterinaria");
L.marker([-34.6385346, -58.4868493]).addTo(mapa).bindPopup("Veterinaria");
L.marker([-34.6478751, -58.4915966]).addTo(mapa).bindPopup("Veterinaria");

navigator.geolocation.getCurrentPosition(success, error);

let marca, circulo, zoom;

let lastLat = null;
let lastLng = null;

function pollLocation() {
    navigator.geolocation.getCurrentPosition(
        pos => {
            const lat = pos.coords.latitude;
            const lng = pos.coords.longitude;

            if(lat !== lastLat || lng !== lastLng) {
                lastLat = lat;
                lastLng = lng;
                success(pos); 
            } else {
                error(); 
            }
        }
    );
}

setInterval(pollLocation, 500);

function success(pos) {
    const lat = pos.coords.latitude;
    const lng = pos.coords.longitude;
    const accuracy = pos.coords.accuracy;

    if(marca) {
        mapa.removeLayer(marca);
        mapa.removeLayer(circulo); 
    }

    marca = L.marker([lat, lng]).addTo(mapa);
    circulo = L.circle([lat, lng], {radius: accuracy}).addTo(mapa);
    mapa.setView([lat, lng], 10);

    if(!zoom) {
        zoom = mapa.fitBounds(circulo.getBounds());
    }

}

function error(err) {
    //alert("Ubicacion no dada")
}