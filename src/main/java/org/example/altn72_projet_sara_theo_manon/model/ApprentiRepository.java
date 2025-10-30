package org.example.altn72_projet_sara_theo_manon.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ApprentiRepository extends JpaRepository<Apprenti, Integer> {
    List<Apprenti> findByNom(String nom);

    List<Apprenti> findByEntreprise_Id(Integer entrepriseId);

    List<Apprenti> findByMission_IdIn(Collection<Integer> missionIds);

    List<Apprenti> findByAnneeAcademique(Integer anneeAcademique);

    @Modifying
    @Query(value = "UPDATE Apprenti a SET a.archive = true WHERE a.id = :id")
    void updateArchive(@Param("id") Integer id);
}
