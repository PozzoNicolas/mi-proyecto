package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/perfil")
public class ControladorPerfil {

    @GetMapping
    public ModelAndView verPerfil (HttpSession session){
        Usuario usuarioActual =  (Usuario)session.getAttribute("usuarioActual");
        if (usuarioActual == null){
            return new ModelAndView("redirect:/login");
        }
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", usuarioActual);
        return new ModelAndView("perfil", modelo);

    }
}
