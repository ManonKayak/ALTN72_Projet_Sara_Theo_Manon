package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.example.altn72_projet_sara_theo_manon.errors.NotFoundException;
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

    public EntrepriseApi(EntrepriseRepository repo) {
        this.repo = repo;
    }

    public record EntrepriseInput(
            @NotBlank String raisonSociale,
            @NotBlank String adresse,
            String infos
    ) {}

    @GetMapping
    public List<Entreprise> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Entreprise get(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Entreprise id=" + id + " introuvable"));
    }

    @PostMapping
    public ResponseEntity<Entreprise> create(@RequestBody @Valid EntrepriseInput in) {
        var e = new Entreprise();
        e.setRaisonSociale(in.raisonSociale());
        e.setAdresse(in.adresse());
        e.setInfos(in.infos());
        var saved = repo.save(e);
        return ResponseEntity.created(URI.create("/api/entreprises/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Entreprise update(@PathVariable Integer id, @RequestBody @Valid EntrepriseInput in) {
        var e = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Entreprise id=" + id + " introuvable"));
        e.setRaisonSociale(in.raisonSociale());
        e.setAdresse(in.adresse());
        e.setInfos(in.infos());
        return repo.save(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) throw new NotFoundException("Entreprise id=" + id + " introuvable");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
