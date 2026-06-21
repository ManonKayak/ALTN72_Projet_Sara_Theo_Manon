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

@WebMvcTest(VisiteApi.class)
@WithMockUser
class VisiteApiWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private VisiteRepository repo;
    @MockitoBean private ApprentiRepository apprentiRepo;
    @MockitoBean private EntrepriseRepository entrepriseRepo;

    private Visite sample() {
        Apprenti a = new Apprenti();
        a.setId(1); a.setNom("Martin"); a.setPrenom("Alice");
        a.setMail("alice@efrei.fr"); a.setTelephone("0600000000");
        a.setRemarques(""); a.setNiveau(3); a.setAnneeAcademique(2024);
        a.setMajeure(1); a.setArchive(false);

        Entreprise e = new Entreprise();
        e.setId(1); e.setRaisonSociale("Corp"); e.setAdresse("1 rue");

        Visite v = new Visite();
        v.setId(1);
        v.setDate(20250601);
        v.setFormat(1);
        v.setCommentaire("Visite OK");
        v.setApprenti(a);
        v.setEntreprise(e);
        return v;
    }

    private void stubDependencies() {
        Apprenti a = new Apprenti();
        a.setId(1); a.setNom("Martin"); a.setPrenom("Alice");
        a.setMail("alice@efrei.fr"); a.setTelephone("0600000000");
        a.setRemarques(""); a.setNiveau(3); a.setAnneeAcademique(2024);
        a.setMajeure(1); a.setArchive(false);
        Entreprise e = new Entreprise();
        e.setId(1); e.setRaisonSociale("Corp"); e.setAdresse("1 rue");
        when(apprentiRepo.findById(1)).thenReturn(Optional.of(a));
        when(entrepriseRepo.findById(1)).thenReturn(Optional.of(e));
    }

    private static final String VALID_BODY = """
            {"date":20250601,"format":1,"commentaire":"Visite OK","apprentiId":1,"entrepriseId":1}
            """;

    @Test
    void list_returnsOk() throws Exception {
        when(repo.findAll()).thenReturn(List.of(sample()));
        mockMvc.perform(get("/api/visites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value(20250601));
    }

    @Test
    void get_found_returns200() throws Exception {
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        mockMvc.perform(get("/api/visites/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.commentaire").value("Visite OK"));
    }

    @Test
    void get_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/visites/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void create_valid_returns201() throws Exception {
        stubDependencies();
        when(repo.save(any())).thenReturn(sample());
        mockMvc.perform(post("/api/visites").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/visites/1"));
    }

    @Test
    void create_missingDate_returns400() throws Exception {
        mockMvc.perform(post("/api/visites").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"format":1,"apprentiId":1,"entrepriseId":1}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_invalidApprentiId_returns400() throws Exception {
        when(apprentiRepo.findById(99)).thenReturn(Optional.empty());
        Entreprise e = new Entreprise();
        e.setId(1); e.setRaisonSociale("Corp"); e.setAdresse("1 rue");
        when(entrepriseRepo.findById(1)).thenReturn(Optional.of(e));

        mockMvc.perform(post("/api/visites").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"date":20250601,"format":1,"apprentiId":99,"entrepriseId":1}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_found_returns200() throws Exception {
        stubDependencies();
        Visite updated = sample();
        updated.setCommentaire("Mise à jour");
        when(repo.findById(1)).thenReturn(Optional.of(sample()));
        when(repo.save(any())).thenReturn(updated);
        mockMvc.perform(put("/api/visites/1").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isOk());
    }

    @Test
    void update_notFound_returns404() throws Exception {
        when(repo.findById(99)).thenReturn(Optional.empty());
        mockMvc.perform(put("/api/visites/99").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(VALID_BODY))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_found_returns204() throws Exception {
        when(repo.existsById(1)).thenReturn(true);
        mockMvc.perform(delete("/api/visites/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_notFound_returns404() throws Exception {
        when(repo.existsById(99)).thenReturn(false);
        mockMvc.perform(delete("/api/visites/99").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
