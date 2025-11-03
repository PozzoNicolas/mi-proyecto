// Mostrar / ocultar modal
const modal = document.getElementById('modalAgenda');
const abrir = document.getElementById('boton-agenda');
const cerrar = document.getElementById('cerrarAgenda');

abrir.onclick = () => modal.style.display = 'flex';
cerrar.onclick = () => modal.style.display = 'none';

window.onclick = (e) => {
    if (e.target === modal) modal.style.display = 'none';
};

// ===========================
// Lógica interna de calendario
// ===========================
var app = {
    settings: {
        container: $('.calendar'),
        calendar: $('.front'),
        days: $('.weeks span'),
        form: $('.back'),
        input: $('.back input'),
        buttons: $('.back button')
    },

    init: function() {
        instance = this;
        settings = this.settings;
        this.bindUIActions();
    },

    swap: function(currentSide, desiredSide) {
        settings.container.toggleClass('flip');
        currentSide.fadeOut(900);
        currentSide.hide();
        desiredSide.show();
    },

    bindUIActions: function() {
        settings.days.on('click', function(){
            instance.swap(settings.calendar, settings.form);
            settings.input.focus();
        });
        settings.buttons.on('click', function(){
            instance.swap(settings.form, settings.calendar);
        });
    }
};

app.init();



