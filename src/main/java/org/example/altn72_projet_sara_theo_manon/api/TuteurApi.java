package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    public record TuteurInput(String poste, String nom, String prenom, String email,
                              String telephone, String remarques, Integer entrepriseId) {}

    @GetMapping
    public List<Tuteur> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Tuteur> get(@PathVariable Integer id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Tuteur> create(@RequestBody TuteurInput in) {
        var t = new Tuteur();
        t.setPoste(in.poste());
        t.setNom(in.nom());
        t.setPrenom(in.prenom());
        t.setEmail(in.email());
        t.setTelephone(in.telephone());
        t.setRemarques(in.remarques());

        if (in.entrepriseId() != null) {
            Entreprise e = entrepriseRepo.findById(in.entrepriseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise introuvable"));
            t.setEntreprise(e);
        }

        var saved = repo.save(t);
        return ResponseEntity.created(URI.create("/api/tuteurs/" + saved.getId()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tuteur> update(@PathVariable Integer id, @RequestBody TuteurInput in) {
        return repo.findById(id).map(existing -> {
            existing.setPoste(in.poste());
            existing.setNom(in.nom());
            existing.setPrenom(in.prenom());
            existing.setEmail(in.email());
            existing.setTelephone(in.telephone());
            existing.setRemarques(in.remarques());

            if (in.entrepriseId() != null) {
                Entreprise e = entrepriseRepo.findById(in.entrepriseId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise introuvable"));
                existing.setEntreprise(e);
            } else {
                existing.setEntreprise(null);
            }

            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
