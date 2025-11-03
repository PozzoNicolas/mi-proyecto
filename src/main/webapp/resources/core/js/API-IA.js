/* --------------------------
   MODAL HANDLING
--------------------------- */
const modal = document.getElementById("modalChatGPT");
const abrir = document.getElementById("abrirChatGPT");
const cerrar = document.getElementById("cerrarChatGPT");

abrir.onclick = () => modal.style.display = "flex";
cerrar.onclick = () => modal.style.display = "none";
window.onclick = (event) => { 
    if (event.target === modal) modal.style.display = "none"; 
};

/* --------------------------
   CHAT FUNCTIONALITY
--------------------------- */
document.getElementById("enviarChatGPT").addEventListener("click", async () => {
    const input = document.getElementById("mensajeChatGPT");
    const mensaje = input.value.trim();
    if (!mensaje) return;

    const chatBox = document.getElementById("chat-box");
    chatBox.innerHTML += `<p><strong>T\u00FA</strong> ${mensaje}</p>`;
    input.value = "";
    chatBox.scrollTop = chatBox.scrollHeight;

    try {
        // Call your Spring backend proxy
        const response = await fetch("/spring/api/chat", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ mensaje })
        });

        if (!response.ok) {
            throw new Error("Error en la conexión con el servidor");
        }

        const data = await response.json();

        chatBox.innerHTML += `<p><strong>VetGPT:</strong> ${data.respuesta}</p>`;
        chatBox.scrollTop = chatBox.scrollHeight;

    } catch (error) {
        chatBox.innerHTML += `<p style="color:red;">Error: ${error.message}</p>`;
    }
});
