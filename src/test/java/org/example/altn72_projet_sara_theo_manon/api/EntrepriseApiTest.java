package org.example.altn72_projet_sara_theo_manon.api;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EntrepriseApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static Integer createdId;

    @Test
    @Order(1)
    void list_returnsOk() throws Exception {
        mockMvc.perform(get("/api/entreprises"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    void create_returnsCreated() throws Exception {
        String body = """
                {"raisonSociale":"Test SA","adresse":"1 rue Test","infos":"Infos"}
                """;
        String location = mockMvc.perform(post("/api/entreprises")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        assertNotNull(location);
        createdId = Integer.parseInt(location.substring(location.lastIndexOf('/') + 1));
    }

    @Test
    @Order(3)
    void get_returnsEntreprise() throws Exception {
        mockMvc.perform(get("/api/entreprises/" + createdId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.raisonSociale").value("Test SA"))
                .andExpect(jsonPath("$.adresse").value("1 rue Test"));
    }

    @Test
    @Order(4)
    void update_returnsUpdated() throws Exception {
        String body = """
                {"raisonSociale":"Updated SA","adresse":"2 rue Update"}
                """;
        mockMvc.perform(put("/api/entreprises/" + createdId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.raisonSociale").value("Updated SA"));
    }

    @Test
    @Order(5)
    void get_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/entreprises/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void update_unknownId_returns404() throws Exception {
        mockMvc.perform(put("/api/entreprises/99999")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"raisonSociale":"X","adresse":"X"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void create_blankFields_returns400() throws Exception {
        mockMvc.perform(post("/api/entreprises")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"raisonSociale":"","adresse":""}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void create_malformedJson_returns400() throws Exception {
        String malformed = "{" + "invalid";
        mockMvc.perform(post("/api/entreprises")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformed))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/entreprises/" + createdId)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void delete_unknownId_returns404() throws Exception {
        mockMvc.perform(delete("/api/entreprises/99999")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
