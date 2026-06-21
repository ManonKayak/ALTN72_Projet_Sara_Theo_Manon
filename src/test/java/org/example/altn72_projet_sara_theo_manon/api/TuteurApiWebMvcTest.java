package org.example.altn72_projet_sara_theo_manon.api;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
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

@WebMvcTest(TuteurApi.class)
@WithMockUser
class TuteurApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private TuteurRepository repo;
    @MockitoBean private EntrepriseRepository entrepriseRepo;

    private Tuteur sample() {
        Tuteur t = new Tuteur();
        t.setId(1);
        t.setNom("Dupont");
        t.setPrenom("Jean");
        t.setEmail("jean.dupont@corp.fr");
        t.setTelephone("0600000000");
        t.setPoste("Directeur technique");
        t.setRemarques("Disponible");
        return t;
    }

    private static final String VALID_BODY = """
            {"nom":"Dupont","prenom":"Jean","email":"jean.dupont@corp.fr",
             "telephone":"0600000000","poste":"Directeur technique","remarques":"Disponible"}
            """;

    @Test
    void list_returnsOk() throws Exception {
        when(repo.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/api/tuteurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Dupont"));
    }

    @Test
    void get_found_returns200() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/api/tuteurs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Dupont"))
                .andExpect(jsonPath("$.poste").value("Directeur technique"));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/tuteurs/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_valid_returns201() throws Exception {
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/tuteurs").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/tuteurs/1"));
    }

    @Test
    void create_withEntreprise_returns201() throws Exception {
        Entreprise e = new Entreprise();
        e.setId(1); e.setRaisonSociale("Corp"); e.setAdresse("1 rue");
        when(entrepriseRepo.findById(1)).thenReturn(Optional.of(e));
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/tuteurs").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Dupont","prenom":"Jean","email":"jean@corp.fr",
                                 "telephone":"0600000000","poste":"CTO","entrepriseId":1}
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void create_blankNom_returns400() throws Exception {
        mockMvc.perform(post("/api/tuteurs").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"","prenom":"Jean","email":"jean@corp.fr","poste":"CTO"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_found_returns200() throws Exception {
        Tuteur updated = sample();
        updated.setPoste("CTO");
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        when(repo.save(any())).thenReturn(updated);
        mockMvc.perform(put("/api/tuteurs/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isOk());
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/tuteurs/99").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returns204() throws Exception {
        when(repo.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/api/tuteurs/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.existsById(99)).thenReturn(false);
        mockMvc.perform(delete("/api/tuteurs/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
