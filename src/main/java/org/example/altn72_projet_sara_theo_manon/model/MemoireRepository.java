package org.example.altn72_projet_sara_theo_manon.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoireRepository extends JpaRepository<Memoire, Integer> {
    Page<Memoire> findBySujetContainingIgnoreCaseOrCommentaireContainingIgnoreCase(
            String sujet, String commentaire, Pageable pageable
    );
}
