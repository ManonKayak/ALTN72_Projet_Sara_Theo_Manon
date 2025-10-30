package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.transaction.Transactional;
import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.model.VisiteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisiteService {

    private final VisiteRepository visiteRepository;

    public VisiteService (final VisiteRepository visiteRepository)
    {
        this.visiteRepository = visiteRepository;
    }

    public List<Visite> getAllVisite()
    {
        return this.visiteRepository.findAll();
    }

    public Optional<Visite> getVisiteById(final int id)
    {
        return this.visiteRepository.findById(id); //.orElseThrow(() -> new IllegalStateException("Cet visite n'existe pas"));
    }

    @Transactional
    public Visite addVisite(final Visite visite)
    {
        return this.visiteRepository.save(visite);
    }

    @Transactional
    public Visite deleteVisite(final Integer id){
        Visite visite = visiteRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet visite n'existe pas"));
        visiteRepository.delete(visite);
        return visite;
    }


    @Transactional
    public Visite updateVisite(final Integer id, final Visite visite){
        Visite visiteToUpdate = visiteRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet visite n'existe pas"));

        if (visiteToUpdate != null) {
            BeanUtils.copyProperties(visite, visiteToUpdate, "id");
            visiteRepository.save(visiteToUpdate);
        }
        return visiteToUpdate;
    }

}
