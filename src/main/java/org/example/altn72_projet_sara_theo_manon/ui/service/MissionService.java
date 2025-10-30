package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.transaction.Transactional;
import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.model.MissionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionService (final MissionRepository missionRepository)
    {
        this.missionRepository = missionRepository;
    }

    public List<Mission> getAllMission()
    {
        return this.missionRepository.findAll();
    }

    public Optional<Mission> getMissionById(final int id)
    {
        return this.missionRepository.findById(id); //.orElseThrow(() -> new IllegalStateException("Cet mission n'existe pas"));
    }

    @Transactional
    public Mission addMission(final Mission mission)
    {
        return this.missionRepository.save(mission);
    }

    @Transactional
    public Mission deleteMission(final Integer id){
        Mission mission = missionRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet mission n'existe pas"));
        missionRepository.delete(mission);
        return mission;
    }


    @Transactional
    public Mission updateMission(final Integer id, final Mission mission){
        Mission missionToUpdate = missionRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet mission n'existe pas"));

        if (missionToUpdate != null) {
            BeanUtils.copyProperties(mission, missionToUpdate, "id");
            missionRepository.save(missionToUpdate);
        }
        return missionToUpdate;
    }

    public List<Mission> getMissionByMotsCles(final String motCle)
    {
        return missionRepository.findByMotsCles(motCle);
    }

}
