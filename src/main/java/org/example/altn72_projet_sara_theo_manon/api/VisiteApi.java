package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.altn72_projet_sara_theo_manon.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.util.List;

@Tag(name = "Visites")
@RestController
@RequestMapping("/api/visites")
public class VisiteApi {
    private final VisiteRepository repo;
    private final ApprentiRepository apprentiRepo;
    private final EntrepriseRepository entrepriseRepo;

    public VisiteApi(VisiteRepository repo, ApprentiRepository apprentiRepo, EntrepriseRepository entrepriseRepo) {
        this.repo = repo;
        this.apprentiRepo = apprentiRepo;
        this.entrepriseRepo = entrepriseRepo;
    }

    public record VisiteInput(Integer date, Integer format, String commentaire,
                              Integer apprentiId, Integer entrepriseId) {}

    @GetMapping public List<Visite> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Visite> get(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Visite> create(@RequestBody VisiteInput in) {
        var v = new Visite();
        applyFields(v, in);
        var saved = repo.save(v);
        return ResponseEntity.created(URI.create("/api/visites/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Visite> update(@PathVariable Integer id, @RequestBody VisiteInput in) {
        return repo.findById(id).map(existing -> {
            applyFields(existing, in);
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    private void applyFields(Visite v, VisiteInput in) {
        v.setDate(in.date());
        v.setFormat(in.format());
        v.setCommentaire(in.commentaire());
        var a = apprentiRepo.findById(in.apprentiId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Apprenti introuvable"));
        var e = entrepriseRepo.findById(in.entrepriseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Entreprise introuvable"));
        v.setApprenti(a);
        v.setEntreprise(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
