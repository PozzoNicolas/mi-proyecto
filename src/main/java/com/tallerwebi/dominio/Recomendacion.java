package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Recomendacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;          // Nombre del producto o consejo
    private String descripcion;     // Descripción o recomendación
    private String tipo;            // Puede seguir siendo "Perro", "Gato", etc.
    private String etapa;           // Cachorro, Adulto, Senior
    private String sexo;            // Macho, Hembra o Ambos
    private String imagen;          // Ruta o URL de la imagen
    private String link;            // Link al producto real o artículo recomendado

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // 🔹 Constructor vacío obligatorio para JPA
    public Recomendacion() {}

    // 🔹 Constructor completo (útil para crear desde el back o tests)
    public Recomendacion(String titulo, String descripcion, String tipo, String etapa, String sexo,
                         String imagen, String link, Usuario usuario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.etapa = etapa;
        this.sexo = sexo;
        this.imagen = imagen;
        this.link = link;
        this.usuario = usuario;
    }

    // 🔹 Constructor rápido (para cuando aún no tenés usuario)
    public Recomendacion(String titulo, String descripcion, String tipo, String etapa, String sexo) {
        this(titulo, descripcion, tipo, etapa, sexo, null, null, null);
    }

    @Override
    public String toString() {
        return "Recomendacion{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", etapa='" + etapa + '\'' +
                ", sexo='" + sexo + '\'' +
                ", usuario=" + (usuario != null ? usuario.getNombre() : "sin usuario") +
                '}';
    }

    // === Getters y Setters ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEtapa() { return etapa; }
    public void setEtapa(String etapa) { this.etapa = etapa; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
