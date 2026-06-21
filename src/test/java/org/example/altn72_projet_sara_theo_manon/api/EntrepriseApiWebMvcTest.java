package org.example.altn72_projet_sara_theo_manon.api;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EntrepriseApi.class)
@WithMockUser
class EntrepriseApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EntrepriseRepository repo;

    private Entreprise sample() {
        Entreprise e = new Entreprise();
        e.setId(1);
        e.setRaisonSociale("Test SA");
        e.setAdresse("1 rue Test");
        e.setInfos("Info");
        return e;
    }

    @Test
    void list_returnsOkAndJson() throws Exception {
        when(repo.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/api/entreprises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].raisonSociale").value("Test SA"));
    }

    @Test
    void get_found_returns200() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/api/entreprises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.raisonSociale").value("Test SA"))
                .andExpect(jsonPath("$.adresse").value("1 rue Test"));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/entreprises/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_valid_returns201() throws Exception {
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/entreprises").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"raisonSociale":"Test SA","adresse":"1 rue Test"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/entreprises/1"));
    }

    @Test
    void create_blankFields_returns400() throws Exception {
        mockMvc.perform(post("/api/entreprises").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"raisonSociale":"","adresse":""}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_found_returns200() throws Exception {
        Entreprise updated = sample();
        updated.setRaisonSociale("Updated SA");
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        when(repo.save(any())).thenReturn(updated);
        mockMvc.perform(put("/api/entreprises/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"raisonSociale":"Updated SA","adresse":"2 rue Update"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.raisonSociale").value("Updated SA"));
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/entreprises/99").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"raisonSociale":"X","adresse":"X"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returns204() throws Exception {
        when(repo.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/api/entreprises/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.existsById(99)).thenReturn(false);
        mockMvc.perform(delete("/api/entreprises/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
