package org.example.altn72_projet_sara_theo_manon.api;

import org.example.altn72_projet_sara_theo_manon.model.*;
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
class ApprentiApiTest {

    @Autowired
    private MockMvc mockMvc;

    private static Integer entrepriseId;
    private static Integer missionId;
    private static Integer tuteurId;
    private static Integer apprentiId;

    @BeforeAll
    static void createFixtures(@Autowired EntrepriseRepository eRepo,
                               @Autowired MissionRepository mRepo,
                               @Autowired TuteurRepository tRepo) {
        Entreprise e = new Entreprise();
        e.setRaisonSociale("Fixture Corp");
        e.setAdresse("1 rue Fixture");
        entrepriseId = eRepo.save(e).getId();

        Mission m = new Mission();
        m.setMotsCles("Java");
        m.setMetierCible("Dev");
        m.setCommentaires("OK");
        missionId = mRepo.save(m).getId();

        Tuteur t = new Tuteur();
        t.setNom("Dupont");
        t.setPrenom("Jean");
        t.setEmail("jean@corp.fr");
        t.setTelephone("0600000000");
        t.setPoste("CTO");
        t.setRemarques("");
        tuteurId = tRepo.save(t).getId();
    }

    private String buildBody(String nom) {
        return String.format("""
                {
                  "anneeAcademique": 2024,
                  "majeure": 1,
                  "nom": "%s",
                  "prenom": "Alice",
                  "mail": "alice@efrei.fr",
                  "telephone": "0600000000",
                  "remarques": "",
                  "niveau": 3,
                  "archive": false,
                  "entrepriseId": %d,
                  "missionId": %d,
                  "tuteurId": %d,
                  "memoireId": null
                }
                """, nom, entrepriseId, missionId, tuteurId);
    }

    @Test
    @Order(1)
    void list_returnsOk() throws Exception {
        mockMvc.perform(get("/api/apprentis"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @Order(2)
    void create_returnsCreated() throws Exception {
        String location = mockMvc.perform(post("/api/apprentis")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildBody("Martin")))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getHeader("Location");

        assertNotNull(location);
        apprentiId = Integer.parseInt(location.substring(location.lastIndexOf('/') + 1));
    }

    @Test
    @Order(3)
    void get_returnsApprenti() throws Exception {
        mockMvc.perform(get("/api/apprentis/" + apprentiId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Martin"))
                .andExpect(jsonPath("$.archive").value(false));
    }

    @Test
    @Order(4)
    void update_returnsUpdated() throws Exception {
        mockMvc.perform(put("/api/apprentis/" + apprentiId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildBody("Durand")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Durand"));
    }

    @Test
    @Order(5)
    void get_unknownId_returns404() throws Exception {
        mockMvc.perform(get("/api/apprentis/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(6)
    void update_unknownId_returns404() throws Exception {
        mockMvc.perform(put("/api/apprentis/99999")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(buildBody("X")))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(7)
    void create_invalidEntrepriseId_returns400() throws Exception {
        String body = String.format("""
                {
                  "anneeAcademique": 2024, "majeure": 1,
                  "nom": "Test", "prenom": "Test",
                  "mail": "test@efrei.fr", "telephone": "0600000000",
                  "remarques": "", "niveau": 1, "archive": false,
                  "entrepriseId": 99999, "missionId": %d, "tuteurId": %d
                }
                """, missionId, tuteurId);

        mockMvc.perform(post("/api/apprentis")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    void create_missingRequiredField_returns400() throws Exception {
        mockMvc.perform(post("/api/apprentis")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nom":"Test"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/apprentis/" + apprentiId)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(10)
    void delete_unknownId_returns404() throws Exception {
        mockMvc.perform(delete("/api/apprentis/99999")
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
