document.addEventListener("DOMContentLoaded", function() {
    const modal = document.getElementById("modalChatGPT");
    const abrir = document.getElementById("abrirChatGPT");
    const cerrar = document.getElementById("cerrarChatGPT");
    const enviar = document.getElementById("enviarChatGPT");
    const input = document.getElementById("mensajeChatGPT");
    const chatBox = document.getElementById("chat-box");

    abrir.onclick = () => modal.style.display = "flex";
    cerrar.onclick = () => modal.style.display = "none";
    window.onclick = (e) => { if (e.target === modal) modal.style.display = "none"; };

    enviar.onclick = async () => {
        const mensaje = input.value.trim();
        if (!mensaje) return;

        chatBox.innerHTML += `<p><strong>T&uacute;:</strong> ${mensaje}</p>`;
        input.value = "";

        const respuesta = await fetch("/api/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(mensaje)
        });

        const texto = await respuesta.text();
        chatBox.innerHTML += `<p><strong>VetConnect:</strong> ${texto}</p>`;
        chatBox.scrollTop = chatBox.scrollHeight;
    };
});