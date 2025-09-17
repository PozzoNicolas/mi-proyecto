package com.tallerwebi.TDD;

import org.springframework.stereotype.Component;

@Component
public class TDD {

    public String validarFortaleza(String contraseña) {

        if (contraseña.length() < 8 && !tieneCaraterEspecial(contraseña)) {
            return "INVALIDA";
        } else if (contraseña.length() >= 8 && !tieneCaraterEspecial(contraseña) && !cumpleConCantDeDigitos(contraseña)){
            return "DEBIL";
        } else if (contraseña.length() >= 4 && tieneCaraterEspecial(contraseña) && !esFuerte(contraseña)){
            return "MEDIANA";
        } else if (esFuerte(contraseña)){
            return "FUERTE";
        }

        return "";
    }

    public static boolean esFuerte(String contraseña) {

        if(contraseña.length() < 8){
            return false;
        }
        if(tieneCaraterEspecial(contraseña) && cumpleConCantDeDigitos(contraseña)){
            return true;
        }
        return false;
    }

    public static boolean tieneCaraterEspecial(String contraseña) {
        for (int i = 0; i < contraseña.length(); i++) {
            if (!Character.isLetterOrDigit(contraseña.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean cumpleConCantDeDigitos(String contraseña) {
        int contador = 0;
        for (int i = 0; i < contraseña.length(); i++) {
            if (Character.isDigit(contraseña.charAt(i))) {
                contador++;
            }
            if (contador >= 4) {
                return true;
            }
        }
        return false;
    }

}
