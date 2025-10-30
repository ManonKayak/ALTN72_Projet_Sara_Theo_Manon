package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
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

    public MissionApi(MissionRepository repo) { this.repo = repo; }

    public record MissionInput(String motsCles, String metierCible, String commentaires) {}

    @GetMapping public List<Mission> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Mission> get(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Mission> create(@RequestBody MissionInput in) {
        var m = new Mission();
        m.setMotsCles(in.motsCles());
        m.setMetierCible(in.metierCible());
        m.setCommentaires(in.commentaires());
        var saved = repo.save(m);
        return ResponseEntity.created(URI.create("/api/missions/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mission> update(@PathVariable Integer id, @RequestBody MissionInput in) {
        return repo.findById(id).map(existing -> {
            existing.setMotsCles(in.motsCles());
            existing.setMetierCible(in.metierCible());
            existing.setCommentaires(in.commentaires());
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
