package org.example.altn72_projet_sara_theo_manon.model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission,Integer> {
    Page<Mission> findByMotsClesContainingIgnoreCaseOrMetierCibleContainingIgnoreCase(String motsCles, String metierCible, Pageable pageable);
}
