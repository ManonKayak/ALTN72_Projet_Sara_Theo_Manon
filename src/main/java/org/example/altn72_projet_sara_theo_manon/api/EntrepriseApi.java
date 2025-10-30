package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Tag(name = "Entreprises")
@RestController
@RequestMapping("/api/entreprises")
public class EntrepriseApi {
    private final EntrepriseRepository repo;

    public EntrepriseApi(EntrepriseRepository repo) { this.repo = repo; }

    public record EntrepriseInput(String raisonSociale, String adresse, String infos) {}

    @GetMapping public List<Entreprise> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Entreprise> get(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Entreprise> create(@RequestBody EntrepriseInput in) {
        var e = new Entreprise();
        e.setRaisonSociale(in.raisonSociale());
        e.setAdresse(in.adresse());
        e.setInfos(in.infos());
        var saved = repo.save(e);
        return ResponseEntity.created(URI.create("/api/entreprises/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entreprise> update(@PathVariable Integer id, @RequestBody EntrepriseInput in) {
        return repo.findById(id).map(existing -> {
            existing.setRaisonSociale(in.raisonSociale());
            existing.setAdresse(in.adresse());
            existing.setInfos(in.infos());
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
