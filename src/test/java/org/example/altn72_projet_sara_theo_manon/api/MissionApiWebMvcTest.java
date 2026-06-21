package org.example.altn72_projet_sara_theo_manon.api;

import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.model.MissionRepository;
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

@WebMvcTest(MissionApi.class)
@WithMockUser
class MissionApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MissionRepository repo;

    private Mission sample() {
        Mission m = new Mission();
        m.setId(1);
        m.setMotsCles("Java Spring");
        m.setMetierCible("Développeur Backend");
        m.setCommentaires("Mission intéressante");
        return m;
    }

    @Test
    void list_returnsOk() throws Exception {
        when(repo.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/api/missions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].motsCles").value("Java Spring"));
    }

    @Test
    void get_found_returns200() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/api/missions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metierCible").value("Développeur Backend"));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/missions/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_valid_returns201() throws Exception {
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/missions").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"motsCles":"Java","metierCible":"Dev","commentaires":"OK"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/missions/1"));
    }

    @Test
    void create_blankField_returns400() throws Exception {
        mockMvc.perform(post("/api/missions").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"motsCles":"","metierCible":"Dev","commentaires":"OK"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_found_returns200() throws Exception {
        Mission updated = sample();
        updated.setMotsCles("Kubernetes");
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        when(repo.save(any())).thenReturn(updated);
        mockMvc.perform(put("/api/missions/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"motsCles":"Kubernetes","metierCible":"DevOps","commentaires":"OK"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.motsCles").value("Kubernetes"));
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/missions/99").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"motsCles":"X","metierCible":"X","commentaires":"X"}
                                """))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returns204() throws Exception {
        when(repo.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/api/missions/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.existsById(99)).thenReturn(false);
        mockMvc.perform(delete("/api/missions/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
