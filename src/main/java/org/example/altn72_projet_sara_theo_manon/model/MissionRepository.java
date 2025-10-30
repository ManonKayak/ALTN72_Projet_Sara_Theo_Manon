package org.example.altn72_projet_sara_theo_manon.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission,Integer> {
    List<Mission> findByMotsCles(String motsCles);
}
