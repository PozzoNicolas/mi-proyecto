package com.tallerwebi.dominio;

import java.io.OutputStream;

public interface ServicioPDF {
    void generarCredencial(Usuario usuario, OutputStream outputStream) throws Exception;
}