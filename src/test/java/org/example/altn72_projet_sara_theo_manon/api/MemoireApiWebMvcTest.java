package org.example.altn72_projet_sara_theo_manon.api;

import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.model.MemoireRepository;
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

@WebMvcTest(MemoireApi.class)
@WithMockUser
class MemoireApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MemoireRepository repo;

    private Memoire sample() {
        Memoire m = new Memoire();
        m.setId(1);
        m.setSujet("Architecture microservices");
        m.setNote(15.5f);
        m.setCommentaire("Bon travail");
        m.setDateSoutenance(20250615);
        m.setNoteSoutenance(16.0f);
        m.setCommentaireSoutenance("Excellente présentation");
        return m;
    }

    private static final String VALID_BODY = """
            {"sujet":"Architecture microservices","note":15.5,"commentaire":"Bon travail",
             "dateSoutenance":20250615,"noteSoutenance":16.0,"commentaireSoutenance":"Excellente présentation"}
            """;

    @Test
    void list_returnsOk() throws Exception {
        when(repo.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/api/memoires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].sujet").value("Architecture microservices"));
    }

    @Test
    void get_found_returns200() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/api/memoires/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sujet").value("Architecture microservices"))
                .andExpect(jsonPath("$.note").value(15.5));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/memoires/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_valid_returns201() throws Exception {
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/memoires").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/memoires/1"));
    }

    @Test
    void update_found_returns200() throws Exception {
        Memoire updated = sample();
        updated.setSujet("Cloud computing");
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        when(repo.save(any())).thenReturn(updated);
        mockMvc.perform(put("/api/memoires/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sujet").value("Cloud computing"));
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/memoires/99").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returns204() throws Exception {
        when(repo.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/api/memoires/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.existsById(99)).thenReturn(false);
        mockMvc.perform(delete("/api/memoires/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
