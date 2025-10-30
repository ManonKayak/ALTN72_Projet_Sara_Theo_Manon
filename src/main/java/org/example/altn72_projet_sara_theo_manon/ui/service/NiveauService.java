package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.model.Niveau;
import org.example.altn72_projet_sara_theo_manon.model.NiveauRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NiveauService {

    private final NiveauRepository niveauRepository;

    public NiveauService(NiveauRepository niveauRepository) {
        this.niveauRepository = niveauRepository;
    }

    public List<Niveau> GetNiveaux()
    {
        return niveauRepository.findAll();
    }
}
