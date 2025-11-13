document.addEventListener('DOMContentLoaded', function() {
    const contenedorActivo = document.getElementById('enlace-activo-container');
    const dataOculta = document.getElementById('enlaces-data-oculta');
    const btnAnterior = document.getElementById('anterior');
    const btnSiguiente = document.getElementById('siguiente');

    // 1. Extraer los datos de la lista oculta en un array de objetos
    const enlaces = Array.from(dataOculta.querySelectorAll('.enlace-data')).map(el => ({
        nombre: el.querySelector('.nombre').textContent,
        url: el.querySelector('.url').textContent,
        descripcion: el.querySelector('.descripcion').textContent,
        provincia: el.querySelector('.provincia').textContent
    }));

    let indiceActual = 0;

    if (enlaces.length === 0) {
        // Si no hay enlaces, el mensaje de Thymeleaf se queda, y salimos.
        return;
    }

    // 2. Función para renderizar el enlace en el contenedor activo
    function renderizarEnlace(indice) {
        if (indice < 0) {
            indice = enlaces.length - 1; // Loop al final
        } else if (indice >= enlaces.length) {
            indice = 0; // Loop al inicio
        }
        indiceActual = indice;

        const enlace = enlaces[indiceActual];

        // Construir el HTML dinámico
        contenedorActivo.innerHTML = `
            <a href="${enlace.url}" target="_blank" class="fw-bold fs-5 text-success d-block mb-2">${enlace.nombre}</a>
            <p class="text-muted mb-1">${enlace.descripcion}</p>
            <p class="text-secondary mb-0">Ubicación: ${enlace.provincia}</p>
        `;
    }

    // 3. Asignar Eventos a los botones
    btnSiguiente.addEventListener('click', () => {
        renderizarEnlace(indiceActual + 1);
    });

    btnAnterior.addEventListener('click', () => {
        renderizarEnlace(indiceActual - 1);
    });

    // 4. Iniciar: Mostrar el primer enlace
    renderizarEnlace(indiceActual);

    // 5. Opcional: Rotación automática (Descomentar si la quieres)
    /*
    setInterval(() => {
        renderizarEnlace(indiceActual + 1);
    }, 8000); // Rota cada 8 segundos
    */
});