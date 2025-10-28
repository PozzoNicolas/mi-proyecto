document.addEventListener("DOMContentLoaded", () => {
    const items = document.querySelectorAll(".recomendacion-item");
    let index = 0;

    if (items.length > 1) {
        items.forEach((item, i) => {
            item.style.display = i === 0 ? "block" : "none";
        });

        setInterval(() => {
            items[index].style.display = "none";
            index = (index + 1) % items.length;
            items[index].style.display = "block";
        }, 4000);
    }
});

document.addEventListener("DOMContentLoaded", () => {
    const items = document.querySelectorAll(".recomendacion-item");
    let index = 0;

    if (items.length > 0) {
        // Mostrar solo la primera recomendación
        items.forEach((item, i) => {
            item.classList.toggle("activa", i === 0);
        });

        // Rotar cada 4 segundos
        setInterval(() => {
            items[index].classList.remove("activa");
            index = (index + 1) % items.length;
            items[index].classList.add("activa");
        }, 4000);
    }
});