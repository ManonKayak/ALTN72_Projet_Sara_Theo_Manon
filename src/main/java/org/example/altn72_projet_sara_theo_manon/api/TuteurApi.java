package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.example.altn72_projet_sara_theo_manon.errors.BadRequestException;
import org.example.altn72_projet_sara_theo_manon.errors.NotFoundException;
import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Tuteurs")
@RestController
@RequestMapping("/api/tuteurs")
public class TuteurApi {

    private final TuteurRepository repo;
    private final EntrepriseRepository entrepriseRepo;

    public TuteurApi(TuteurRepository repo, EntrepriseRepository entrepriseRepo) {
        this.repo = repo;
        this.entrepriseRepo = entrepriseRepo;
    }

    public record TuteurInput(
            @NotBlank String poste,
            @NotBlank String nom,
            @NotBlank String prenom,
            @Email String email,
            String telephone,
            String remarques,
            Integer entrepriseId
    ) {}

    @GetMapping
    public List<Tuteur> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Tuteur get(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Tuteur id=" + id + " introuvable"));
    }

    @PostMapping
    public ResponseEntity<Tuteur> create(@RequestBody @Valid TuteurInput in) {
        var t = new Tuteur();
        t.setPoste(in.poste());
        t.setNom(in.nom());
        t.setPrenom(in.prenom());
        t.setEmail(in.email());
        t.setTelephone(in.telephone());
        t.setRemarques(in.remarques());

        if (in.entrepriseId() != null) {
            Entreprise e = entrepriseRepo.findById(in.entrepriseId())
                    .orElseThrow(() -> new BadRequestException("Entreprise " + in.entrepriseId() + " inexistante"));
            t.setEntreprise(e);
        }

        var saved = repo.save(t);
        return ResponseEntity.created(URI.create("/api/tuteurs/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Tuteur update(@PathVariable Integer id, @RequestBody @Valid TuteurInput in) {
        var t = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Tuteur id=" + id + " introuvable"));

        t.setPoste(in.poste());
        t.setNom(in.nom());
        t.setPrenom(in.prenom());
        t.setEmail(in.email());
        t.setTelephone(in.telephone());
        t.setRemarques(in.remarques());

        if (in.entrepriseId() != null) {
            var e = entrepriseRepo.findById(in.entrepriseId())
                    .orElseThrow(() -> new BadRequestException("Entreprise " + in.entrepriseId() + " inexistante"));
            t.setEntreprise(e);
        } else {
            t.setEntreprise(null);
        }

        return repo.save(t);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) throw new NotFoundException("Tuteur id=" + id + " introuvable");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
