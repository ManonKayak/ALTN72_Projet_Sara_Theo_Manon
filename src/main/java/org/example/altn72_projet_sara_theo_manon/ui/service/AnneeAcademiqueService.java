package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.model.AnneeAcademique;
import org.example.altn72_projet_sara_theo_manon.model.AnneeAcademiqueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnneeAcademiqueService {

    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    public AnneeAcademiqueService(AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }
    public Optional<AnneeAcademique> getAnneeById(final int id)
    {
        return anneeAcademiqueRepository.findById(id);
    }

    public List<AnneeAcademique> GetAllAnnee()
    {
        return anneeAcademiqueRepository.findAll();
    }
}
