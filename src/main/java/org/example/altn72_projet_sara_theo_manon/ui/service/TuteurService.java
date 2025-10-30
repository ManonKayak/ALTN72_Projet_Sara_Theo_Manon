package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.transaction.Transactional;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TuteurService {

    private final TuteurRepository tuteurRepository;

    public TuteurService (final TuteurRepository tuteurRepository)
    {
        this.tuteurRepository = tuteurRepository;
    }

    public List<Tuteur> getAllTuteur()
    {
        return this.tuteurRepository.findAll();
    }

    public Optional<Tuteur> getTuteurById(final int id)
    {
        return this.tuteurRepository.findById(id); //.orElseThrow(() -> new IllegalStateException("Cet tuteur n'existe pas"));
    }

    @Transactional
    public Tuteur addTuteur(final Tuteur tuteur)
    {
        return this.tuteurRepository.save(tuteur);
    }

    @Transactional
    public Tuteur deleteTuteur(final Integer id){
        Tuteur tuteur = tuteurRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet tuteur n'existe pas"));
        tuteurRepository.delete(tuteur);
        return tuteur;
    }


    @Transactional
    public Tuteur updateTuteur(final Integer id, final Tuteur tuteur){
        Tuteur tuteurToUpdate = tuteurRepository.findById(id).orElseThrow(() -> new IllegalStateException("Cet tuteur n'existe pas"));

        if (tuteurToUpdate != null) {
            BeanUtils.copyProperties(tuteur, tuteurToUpdate, "id");
            tuteurRepository.save(tuteurToUpdate);
        }
        return tuteurToUpdate;
    }

}
