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
    public Apprenti updateApprenti(final Integer id, final Apprenti apprenti){
        Apprenti apprentiToUpdate = apprentiRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet apprenti n'existe pas"));

        if (apprentiToUpdate != null) {
            BeanUtils.copyProperties(apprenti, apprentiToUpdate, "id");
            apprentiRepository.save(apprentiToUpdate);
        }
        return apprentiToUpdate;
    }
    
}
