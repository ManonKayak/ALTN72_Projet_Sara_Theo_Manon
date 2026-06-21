package org.example.altn72_projet_sara_theo_manon.api;

import org.example.altn72_projet_sara_theo_manon.model.*;
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

@WebMvcTest(ApprentiApi.class)
@WithMockUser
class ApprentiApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private ApprentiRepository repo;
    @MockitoBean private EntrepriseRepository entrepriseRepo;
    @MockitoBean private MissionRepository missionRepo;
    @MockitoBean private TuteurRepository tuteurRepo;
    @MockitoBean private MemoireRepository memoireRepo;

    private Apprenti sample() {
        Apprenti a = new Apprenti();
        a.setId(1);
        a.setNom("Martin");
        a.setPrenom("Alice");
        a.setMail("alice@efrei.fr");
        a.setTelephone("0600000000");
        a.setRemarques("");
        a.setNiveau(3);
        a.setAnneeAcademique(2024);
        a.setMajeure(1);
        a.setArchive(false);
        return a;
    }

    private void stubDependencies() {
        Entreprise e = new Entreprise();
        e.setId(1); e.setRaisonSociale("Corp"); e.setAdresse("1 rue");
        Mission m = new Mission();
        m.setId(1); m.setMotsCles("Java"); m.setMetierCible("Dev"); m.setCommentaires("OK");
        Tuteur t = new Tuteur();
        t.setId(1); t.setNom("Dupont"); t.setPrenom("Jean");
        t.setEmail("jean@corp.fr"); t.setTelephone("0600000000"); t.setPoste("CTO");
        when(entrepriseRepo.findById(1)).thenReturn(Optional.of(e));
        when(missionRepo.findById(1)).thenReturn(Optional.of(m));
        when(tuteurRepo.findById(1)).thenReturn(Optional.of(t));
    }

    private String body(String nom) {
        return """
                {"anneeAcademique":2024,"majeure":1,"nom":"%s","prenom":"Alice",
                 "mail":"alice@efrei.fr","telephone":"0600000000","remarques":"",
                 "niveau":3,"archive":false,"entrepriseId":1,"missionId":1,"tuteurId":1,"memoireId":null}
                """.formatted(nom);
    }

    @Test
    void list_returnsOk() throws Exception {
        when(repo.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/api/apprentis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nom").value("Martin"));
    }

    @Test
    void get_found_returns200() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/api/apprentis/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Martin"))
                .andExpect(jsonPath("$.archive").value(false));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/apprentis/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_valid_returns201() throws Exception {
        stubDependencies();
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/apprentis").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Martin")))
                .andExpect(status().isCreated());
    }

    @Test
    void create_missingField_returns400() throws Exception {
        mockMvc.perform(post("/api/apprentis").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Test"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_invalidEntrepriseId_returns400() throws Exception {
        when(entrepriseRepo.findById(99)).thenReturn(Optional.empty());
        Mission m = new Mission();
        m.setId(1); m.setMotsCles("Java"); m.setMetierCible("Dev"); m.setCommentaires("OK");
        Tuteur t = new Tuteur();
        t.setId(1); t.setNom("Dupont"); t.setPrenom("Jean");
        t.setEmail("jean@corp.fr"); t.setTelephone("0600000000"); t.setPoste("CTO");
        when(missionRepo.findById(1)).thenReturn(Optional.of(m));
        when(tuteurRepo.findById(1)).thenReturn(Optional.of(t));

        mockMvc.perform(post("/api/apprentis").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"anneeAcademique":2024,"majeure":1,"nom":"Test","prenom":"Test",
                                 "mail":"test@efrei.fr","telephone":"0600000000","remarques":"",
                                 "niveau":1,"archive":false,"entrepriseId":99,"missionId":1,"tuteurId":1}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_found_returns200() throws Exception {
        stubDependencies();
        Apprenti updated = sample();
        updated.setNom("Durand");
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        when(repo.save(any())).thenReturn(updated);
        mockMvc.perform(put("/api/apprentis/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("Durand")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Durand"));
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/apprentis/99").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body("X")))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returns204() throws Exception {
        when(repo.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/api/apprentis/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.existsById(99)).thenReturn(false);
        mockMvc.perform(delete("/api/apprentis/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
