const mapa = L.map('mi_mapa').setView([-34.6701103, -58.5628770], 5);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(mapa);

L.marker([-34.6701103, -58.5628770]).addTo(mapa).bindPopup("UNLaM");
L.marker([-34.6407308, -58.4804502]).addTo(mapa).bindPopup("Veterinaria");
L.marker([-34.6385346, -58.4868493]).addTo(mapa).bindPopup("Veterinaria");
L.marker([-34.6478751, -58.4915966]).addTo(mapa).bindPopup("Veterinaria");
