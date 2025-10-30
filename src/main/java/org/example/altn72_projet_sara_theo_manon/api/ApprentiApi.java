package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.altn72_projet_sara_theo_manon.errors.BadRequestException;
import org.example.altn72_projet_sara_theo_manon.errors.NotFoundException;
import org.example.altn72_projet_sara_theo_manon.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @NotNull Integer anneeAcademique,
            @NotNull Integer majeure,
            @NotBlank String nom,
            @NotBlank String prenom,
            @Email String mail,
            @NotBlank String telephone,
            String remarques,
            @NotNull Integer niveau,
            @NotNull Boolean archive,
            @NotNull Integer entrepriseId,
            @NotNull Integer missionId,
            @NotNull Integer tuteurId,
            Integer memoireId   // optionnel
    ) {}

    @GetMapping
    public List<Apprenti> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Apprenti get(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Apprenti id=" + id + " introuvable"));
    }

    @PostMapping
    public ResponseEntity<Apprenti> create(@RequestBody @Valid ApprentiInput in) {
        var a = new Apprenti();
        applyInput(a, in);
        var saved = repo.save(a);
        return ResponseEntity.created(URI.create("/api/apprentis/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Apprenti update(@PathVariable Integer id, @RequestBody @Valid ApprentiInput in) {
        var a = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Apprenti id=" + id + " introuvable"));
        applyInput(a, in);
        return repo.save(a);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) throw new NotFoundException("Apprenti id=" + id + " introuvable");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void applyInput(Apprenti a, ApprentiInput in) {
        a.setAnneeAcademique(in.anneeAcademique());
        a.setMajeure(in.majeure());
        a.setNom(in.nom());
        a.setPrenom(in.prenom());
        a.setMail(in.mail());
        a.setTelephone(in.telephone());
        a.setRemarques(in.remarques());
        a.setNiveau(in.niveau());
        a.setArchive(in.archive());

        var e = entrepriseRepo.findById(in.entrepriseId())
                .orElseThrow(() -> new BadRequestException("Entreprise " + in.entrepriseId() + " inexistante"));
        var m = missionRepo.findById(in.missionId())
                .orElseThrow(() -> new BadRequestException("Mission " + in.missionId() + " inexistante"));
        var t = tuteurRepo.findById(in.tuteurId())
                .orElseThrow(() -> new BadRequestException("Tuteur " + in.tuteurId() + " inexistant"));

        a.setEntreprise(e);
        a.setMission(m);
        a.setTuteur(t);

        if (in.memoireId() != null) {
            var mem = memoireRepo.findById(in.memoireId())
                    .orElseThrow(() -> new BadRequestException("Mémoire " + in.memoireId() + " inexistante"));
            a.setMemoire(mem);
        } else {
            a.setMemoire(null);
        }
    }
}
