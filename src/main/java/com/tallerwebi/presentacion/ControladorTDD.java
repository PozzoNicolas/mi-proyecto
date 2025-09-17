package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tallerwebi.TDD.TDD;

@Controller
public class ControladorTDD {

    private final TDD service;

    public ControladorTDD (TDD service){
        this.service = service;
    }

    @GetMapping("/password")
    public String mostrarFormulario(){
        return "password";
    }

    @PostMapping ("/validar-password")
    public String validar (@RequestParam("password") String password, Model model){

        String resultado = service.validarFortaleza(password);
        model.addAttribute("password", password);
        model.addAttribute("resultado", resultado);
        return "password"; // vuelve a la vista con los datos cargados
    }



}
