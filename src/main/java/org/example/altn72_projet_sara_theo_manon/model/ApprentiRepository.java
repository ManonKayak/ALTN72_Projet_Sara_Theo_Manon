package org.example.altn72_projet_sara_theo_manon.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    Page<Apprenti> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(
            String nom, String prenom, Pageable pageable
    );
}
