package com.tallerwebi.punta_a_punta;

import com.tallerwebi.config.SpringWebConfig;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringWebConfig.class })
@WebAppConfiguration
public class RegistrarMascotaE2E {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void registroUsuarioYRegistroMascotaTest() throws Exception {
        // 1️⃣ Registramos un usuario
        mockMvc.perform(post("/nuevo-usuario")
                        .param("nombre", "Nicolas")
                        .param("email", "nico@test.com")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())   // redirige tras registro
                .andExpect(redirectedUrl("/login"));     // ejemplo

        // 2️⃣ Hacemos login con ese usuario
        MvcResult loginResult = mockMvc.perform(post("/login")
                        .param("email", "nico@test.com")
                        .param("password", "1234"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        // Obtenemos sesión del login
        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession(false);
        assertNotNull(session); // la sesión debe existir

        // 3️⃣ Registramos una mascota usando la sesión
        mockMvc.perform(post("/mascotas")
                        .session(session)
                        .param("nombre", "Fido")
                        .param("tipoDeMascota", "Perro")
                        .param("edad", "3")
                        .param("sexo", "Macho")
                .contentType(APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/mis-mascotas"));

        // 4️⃣ Verificamos que la mascota aparece en la lista del usuario
        mockMvc.perform(get("/mis-mascotas").session(session))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("mascotas"))
                .andExpect(model().attribute("mascotas",
                        Matchers.hasItem(Matchers.hasProperty("nombre", Matchers.is("Fido")))));


    }
}
