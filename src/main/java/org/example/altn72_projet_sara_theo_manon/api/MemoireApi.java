package org.example.altn72_projet_sara_theo_manon.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.model.MemoireRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@Tag(name = "Mémoires")
@RestController
@RequestMapping("/api/memoires")
public class MemoireApi {
    private final MemoireRepository repo;

    public MemoireApi(MemoireRepository repo) { this.repo = repo; }

    public record MemoireInput(String sujet, Float note, String commentaire,
                               Integer dateSoutenance, Float noteSoutenance, String commentaireSoutenance) {}

    @GetMapping public List<Memoire> list() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Memoire> get(@PathVariable Integer id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Memoire> create(@RequestBody MemoireInput in) {
        var m = new Memoire();
        m.setSujet(in.sujet());
        m.setNote(in.note());
        m.setCommentaire(in.commentaire());
        m.setDateSoutenance(in.dateSoutenance());
        m.setNoteSoutenance(in.noteSoutenance());
        m.setCommentaireSoutenance(in.commentaireSoutenance());
        var saved = repo.save(m);
        return ResponseEntity.created(URI.create("/api/memoires/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Memoire> update(@PathVariable Integer id, @RequestBody MemoireInput in) {
        return repo.findById(id).map(existing -> {
            existing.setSujet(in.sujet());
            existing.setNote(in.note());
            existing.setCommentaire(in.commentaire());
            existing.setDateSoutenance(in.dateSoutenance());
            existing.setNoteSoutenance(in.noteSoutenance());
            existing.setCommentaireSoutenance(in.commentaireSoutenance());
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
