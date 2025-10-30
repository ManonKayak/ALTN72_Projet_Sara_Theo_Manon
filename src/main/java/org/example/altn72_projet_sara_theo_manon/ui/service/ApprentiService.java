package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.transaction.Transactional;
import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.ApprentiRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApprentiService {
    
    private final ApprentiRepository apprentiRepository;
    
    public ApprentiService (final ApprentiRepository apprentiRepository)
    {
        this.apprentiRepository = apprentiRepository;
    }
    
    public List<Apprenti> getAllApprenti()
    {
        return this.apprentiRepository.findAll();
    }

    public List<Apprenti> getAllApprentiActifs()
    {
        return this.apprentiRepository.findByArchiveFalse();
    }
    
    public Optional<Apprenti> getApprentiById(final int id)
    {
        return this.apprentiRepository.findById(id); //.orElseThrow(() -> new IllegalStateException("Cet apprenti n'existe pas"));
    }
    
    @Transactional
    public Apprenti addApprenti(final Apprenti apprenti)
    {
        return this.apprentiRepository.save(apprenti);
    }
    
    @Transactional
    public Apprenti deleteApprenti(final Integer id){
        Apprenti apprenti = apprentiRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet apprenti n'existe pas"));
        apprentiRepository.delete(apprenti);
        return apprenti;
    }
    
    
    @Transactional
    public boolean updateApprenti(final Integer id, final Apprenti apprenti){
        try {
            Optional<Apprenti> apprentiToUpdate = apprentiRepository.findById(id);

            if (apprentiToUpdate.isPresent()) {
                BeanUtils.copyProperties(apprenti, apprentiToUpdate.get(), "id");
                apprentiRepository.save(apprentiToUpdate.get());
                return true;
            }
            else  {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    public List<Apprenti> FilterApprentiByNom(final String nom)
    {
        var apprentis = apprentiRepository.findByNom(nom);
        return apprentis;
    }

    public List<Apprenti> FilterApprentiByEntreprise_Id(Integer entrepriseId)
    {
        var apprentis = apprentiRepository.findByEntreprise_Id(entrepriseId);
        return apprentis;
    }

    public List<Apprenti> FilterApprentiByAnneeAcademique(Integer anneeAcademique)
    {
        var apprentis = apprentiRepository.findByAnneeAcademique(anneeAcademique);
        return apprentis;
    }

    public List<Apprenti> FilterApprentiByMission(List<Integer> missionIds)
    {
        var apprentis = apprentiRepository.findByMission_IdIn(missionIds);
        return apprentis;
    }

    public void ArchiveApprenti(final int id)
    {
        apprentiRepository.updateArchive(id);
    }
}

