package org.example.altn72_projet_sara_theo_manon.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntrepriseRepository extends JpaRepository<Entreprise,Integer> {
    Entreprise findByRaisonSociale(String raisonSociale);
}
