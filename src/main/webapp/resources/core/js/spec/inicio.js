const modal = document.getElementById("modalAgenda");
const abrir = document.getElementById("abrirAgenda");
const cerrar = document.getElementById("cerrarAgenda");

abrir.onclick = () => {
    modal.style.display = "flex";
    document.body.classList.add("modal-activo");
};

cerrar.onclick = () => {
    modal.style.display = "none";
    document.body.classList.remove("modal-activo");
};

window.onclick = (event) => {
    if (event.target === modal) {
        modal.style.display = "none";
        document.body.classList.remove("modal-activo");
    }
};
