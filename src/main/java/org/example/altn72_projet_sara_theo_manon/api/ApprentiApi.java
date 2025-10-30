package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.altn72_projet_sara_theo_manon.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.List;

@Tag(name = "Apprentis")
@RestController
@RequestMapping("/api/apprentis")
public class ApprentiApi {
    private final ApprentiRepository repo;
    private final EntrepriseRepository entrepriseRepo;
    private final MissionRepository missionRepo;
    private final TuteurRepository tuteurRepo;
    private final MemoireRepository memoireRepo;

    public ApprentiApi(ApprentiRepository repo,
                       EntrepriseRepository entrepriseRepo,
                       MissionRepository missionRepo,
                       TuteurRepository tuteurRepo,
                       MemoireRepository memoireRepo) {
        this.repo = repo;
        this.entrepriseRepo = entrepriseRepo;
        this.missionRepo = missionRepo;
        this.tuteurRepo = tuteurRepo;
        this.memoireRepo = memoireRepo;
    }

    public record ApprentiInput(
            Integer anneeAcademique, Integer majeure, String nom, String prenom,
            String mail, String telephone, String remarques, Integer niveau, Boolean archive,
            Integer entrepriseId, Integer missionId, Integer tuteurId, Integer memoireId) {}

    @GetMapping public List<Apprenti> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Apprenti> get(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Apprenti> create(@RequestBody ApprentiInput in) {
        var a = new Apprenti();
        applyFields(a, in);
        var saved = repo.save(a);
        return ResponseEntity.created(URI.create("/api/apprentis/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Apprenti> update(@PathVariable Integer id, @RequestBody ApprentiInput in) {
        return repo.findById(id).map(existing -> {
            applyFields(existing, in);
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    private void applyFields(Apprenti a, ApprentiInput in) {
        a.setAnneeAcademique(in.anneeAcademique());
        a.setMajeure(in.majeure());
        a.setNom(in.nom());
        a.setPrenom(in.prenom());
        a.setMail(in.mail());
        a.setTelephone(in.telephone());
        a.setRemarques(in.remarques());
        a.setNiveau(in.niveau());
        a.setArchive(Boolean.TRUE.equals(in.archive()));

        // FK obligatoires
        var e = entrepriseRepo.findById(in.entrepriseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise introuvable"));
        var m = missionRepo.findById(in.missionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mission introuvable"));
        var t = tuteurRepo.findById(in.tuteurId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tuteur introuvable"));

        a.setEntreprise(e);
        a.setMission(m);
        a.setTuteur(t);

        // FK optionnelle
        if (in.memoireId() != null) {
            var mem = memoireRepo.findById(in.memoireId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mémoire introuvable"));
            a.setMemoire(mem);
        } else {
            a.setMemoire(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
