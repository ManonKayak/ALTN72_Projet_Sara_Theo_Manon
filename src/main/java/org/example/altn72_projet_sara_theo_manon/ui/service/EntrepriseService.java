package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.transaction.Transactional;
import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseService (final EntrepriseRepository entrepriseRepository)
    {
        this.entrepriseRepository = entrepriseRepository;
    }

    public List<Entreprise> getAllEntreprise()
    {
        return this.entrepriseRepository.findAll();
    }

    public Optional<Entreprise> getEntrepriseById(final int id)
    {
        return this.entrepriseRepository.findById(id); //.orElseThrow(() -> new IllegalStateException("Cet Entreprise n'existe pas"));
    }

    @Transactional
    public Entreprise addEntreprise(final Entreprise Entreprise)
    {
        return this.entrepriseRepository.save(Entreprise);
    }

    @Transactional
    public Entreprise deleteEntreprise(final Integer id){
        Entreprise entreprise = entrepriseRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet entreprise n'existe pas"));
        entrepriseRepository.delete(entreprise);
        return entreprise;
    }


    @Transactional
    public Entreprise updateEntreprise(final Integer id, final Entreprise Entreprise){
        Entreprise entrepriseToUpdate = entrepriseRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet entreprise n'existe pas"));

        if (entrepriseToUpdate != null) {
            BeanUtils.copyProperties(Entreprise, entrepriseToUpdate, "id");
            entrepriseRepository.save(entrepriseToUpdate);
        }
        return entrepriseToUpdate;
    }

}
