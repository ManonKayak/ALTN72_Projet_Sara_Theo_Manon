package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.example.altn72_projet_sara_theo_manon.errors.NotFoundException;
import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.model.MissionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Missions")
@RestController
@RequestMapping("/api/missions")
public class MissionApi {

    private final MissionRepository repo;

    public MissionApi(MissionRepository repo) {
        this.repo = repo;
    }

    public record MissionInput(
            @NotBlank String motsCles,
            @NotBlank String metierCible,
            @NotBlank String commentaires
    ) {}

    @GetMapping
    public List<Mission> list() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public Mission get(@PathVariable Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Mission id=" + id + " introuvable"));
    }

    @PostMapping
    public ResponseEntity<Mission> create(@RequestBody @Valid MissionInput in) {
        var m = new Mission();
        m.setMotsCles(in.motsCles());
        m.setMetierCible(in.metierCible());
        m.setCommentaires(in.commentaires());
        var saved = repo.save(m);
        return ResponseEntity.created(URI.create("/api/missions/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public Mission update(@PathVariable Integer id, @RequestBody @Valid MissionInput in) {
        var m = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Mission id=" + id + " introuvable"));
        m.setMotsCles(in.motsCles());
        m.setMetierCible(in.metierCible());
        m.setCommentaires(in.commentaires());
        return repo.save(m);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (!repo.existsById(id)) throw new NotFoundException("Mission id=" + id + " introuvable");
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
