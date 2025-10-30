package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.example.altn72_projet_sara_theo_manon.errors.BadRequestException;
import org.example.altn72_projet_sara_theo_manon.errors.NotFoundException;
import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.model.ApprentiRepository;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.model.VisiteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Visites")
@RestController
@RequestMapping("/api/visites")
public class VisiteApi {

    private final VisiteRepository repo;
    private final ApprentiRepository apprentiRepo;
    private final EntrepriseRepository entrepriseRepo;

    public VisiteApi(VisiteRepository repo,
                     ApprentiRepository apprentiRepo,
                     EntrepriseRepository entrepriseRepo) {
        this.repo = repo;
        this.apprentiRepo = apprentiRepo;
        this.entrepriseRepo = entrepriseRepo;
    }

    public record VisiteInput(
            @NotNull Integer date,
            Integer format,
            String commentaire,
            @NotNull Integer apprentiId,
            @NotNull Integer entrepriseId
    ) {}

    @GetMapping
    public List<Visite> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Visite get(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Visite id=" + id + " introuvable"));
    }

    @PostMapping
    public ResponseEntity<Visite> create(@RequestBody @Valid VisiteInput in) {
        var v = new Visite();
        applyInput(v, in);
        var saved = repo.save(v);
        return ResponseEntity.created(URI.create("/api/visites/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Visite update(@PathVariable Integer id, @RequestBody @Valid VisiteInput in) {
        var v = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Visite id=" + id + " introuvable"));
        applyInput(v, in);
        return repo.save(v);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) throw new NotFoundException("Visite id=" + id + " introuvable");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void applyInput(Visite v, VisiteInput in) {
        v.setDate(in.date());
        v.setFormat(in.format());
        v.setCommentaire(in.commentaire());

        Apprenti a = apprentiRepo.findById(in.apprentiId())
                .orElseThrow(() -> new BadRequestException("Apprenti " + in.apprentiId() + " inexistant"));
        Entreprise e = entrepriseRepo.findById(in.entrepriseId())
                .orElseThrow(() -> new BadRequestException("Entreprise " + in.entrepriseId() + " inexistante"));

        v.setApprenti(a);
        v.setEntreprise(e);
    }
}
