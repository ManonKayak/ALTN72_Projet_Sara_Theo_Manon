package org.example.altn72_projet_sara_theo_manon.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TuteurRepository extends JpaRepository<Tuteur, Integer> {
    Page<Tuteur> findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(String nom, String prenom, Pageable pageable);
}
