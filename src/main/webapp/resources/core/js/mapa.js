
// ================================
// CONFIGURACIÓN DE MODO DEMO
// ================================

// Activar/desactivar ubicación simulada
const modoDemo = true;

// Ubicaciones de cada integrante
const ubicacionesDemo = {
    "nico": [-34.640, -58.560],  // Ramos Mejía
    "joaco": [-34.670, -58.562],  // San Justo (UNLaM)
    "diego": [-34.650, -58.620]  // Morón
};

// Elegir quién está presentando
const quienPresenta = "nico";

// --- Ícono personalizado para MI UBICACIÓN ---
const iconoMiUbicacion = L.icon({
    iconUrl: "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/images/marker-icon.png", // ícono base de Leaflet
    iconSize: [20, 32],   // más chico que el normal (25,41)
    iconAnchor: [10, 32],
    popupAnchor: [0, -30],
    className: "mi-ubicacion-icon" // para aplicar color por CSS
});
// ================================
// MAPA - Leaflet
// ================================
const mapa = L.map('mi_mapa').setView([-34.6701103, -58.5628770], 12);

L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
    attribution: '© OpenStreetMap contributors'
}).addTo(mapa);

let marcaUsuario, circuloUsuario, zoomRealizado = false;

// ================================
// LISTA DE SUCURSALES
// ================================
const sucursales = [
    { id: 1, nombre: "Veterinaria Ramos", lat: -34.652180, lng: -58.554688 },
    { id: 2, nombre: "Veterinaria San Justo", lat: -34.680390, lng: -58.561161 },
    { id: 3, nombre: "Veterinaria Castelar", lat: -34.659484, lng: -58.638386 }
];

// ================================
// MARCADORES DE SUCURSALES
// ================================
sucursales.forEach(s => {
    L.marker([s.lat, s.lng])
        .addTo(mapa)
        .bindPopup(`
            <b>${s.nombre}</b><br>
            <a href="/spring/turno/nuevo-turno-desde-mapa?v=${s.id}" class="btn-popup"">
    Sacar turno aquí
            </a>
        `);
});

// ================================
// SACAR TURNO
// ================================
function sacarTurno(nombreSucursal) {
    // Podés redirigir al formulario o mostrar un mensaje
    window.location.href = "/nuevo-turno?sucursal=" + encodeURIComponent(nombreSucursal);
}

// ================================
// UBICACIÓN (REAL o DEMO)
// ================================
if (modoDemo) {
    // Usar ubicación simulada
    const [lat, lng] = ubicacionesDemo[quienPresenta];
    mostrarUbicacion({ coords: { latitude: lat, longitude: lng, accuracy: 80 } });
} else {
    // Usar GPS real del navegador
    navigator.geolocation.getCurrentPosition(
        mostrarUbicacion,
        err => console.warn("No se pudo obtener ubicación real")
    );
}
function mostrarUbicacion(pos) {
    const lat = pos.coords.latitude;
    const lng = pos.coords.longitude;
    const accuracy = pos.coords.accuracy;

    // Si ya existe, elimino la anterior
    if (marcaUsuario) {
        mapa.removeLayer(marcaUsuario);
        mapa.removeLayer(circuloUsuario);
    }

    marcaUsuario = L.marker([lat, lng], { icon: iconoMiUbicacion })
        .addTo(mapa)
        .bindPopup("Mi ubicacion");
    circuloUsuario = L.circle([lat, lng], { radius: accuracy }).addTo(mapa);

    // Zoom solo la primera vez
    if (!zoomRealizado) {
        mapa.setView([lat, lng], 14);
        zoomRealizado = true;
    }
}