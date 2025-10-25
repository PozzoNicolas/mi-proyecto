// Mostrar y ocultar modal
const modal = document.getElementById("modalChatGPT");
const abrir = document.getElementById("abrirChatGPT");
const cerrar = document.getElementById("cerrarChatGPT");

abrir.onclick = () => modal.style.display = "flex";
cerrar.onclick = () => modal.style.display = "none";
window.onclick = (event) => { if (event.target === modal) modal.style.display = "none"; };

// Chat funcional
document.getElementById("enviarChatGPT").addEventListener("click", async () => {
    const input = document.getElementById("mensajeChatGPT");
    const mensaje = input.value.trim();
    if (!mensaje) return;

    const chatBox = document.getElementById("chat-box");
    chatBox.innerHTML += `<p><strong>T&uacute;:</strong> ${mensaje}</p>`;
    input.value = "";

    try {
        // Hacemos la solicitud POST al backend
        const response = await fetch("/api/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ mensaje })
        });

        // Verificamos que el servidor respondió bien
        if (!response.ok) {
            throw new Error("Error en la conexion con el servidor");
        }

        // Procesamos la respuesta JSON
        const data = await response.json();

        // Mostramos la respuesta del ChatGPT en pantalla
        chatBox.innerHTML += `<p><strong>VetGPT:</strong> ${data.respuesta}</p>`;
        chatBox.scrollTop = chatBox.scrollHeight;

    } catch (error) {
        chatBox.innerHTML += `<p style="color:red;">Error: ${error.message}</p>`;
    }
});
