package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ControladorMisMascotas {

    @GetMapping("/mis-mascotas")
    public String misMascotas()
    {
        return "mis-mascotas";
    }


}
