package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.EntrepriseViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.EntrepriseView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryEntrepriseWriteService implements EntrepriseWriteService {

    private final EntrepriseRepository repository;
    private final EntrepriseViewMapper mapper;

    public RepositoryEntrepriseWriteService(EntrepriseRepository repository, EntrepriseViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EntrepriseView create(EntrepriseForm form) {
        var e = new Entreprise();
        e.setRaisonSociale(form.raisonSociale());
        e.setAdresse(form.adresse());
        e.setInfos(form.infos());
        return mapper.toView(repository.save(e));
    }

    @Override
    public Optional<EntrepriseView> update(Integer id, EntrepriseForm form) {
        return repository.findById(id).map(e -> {
            e.setRaisonSociale(form.raisonSociale());
            e.setAdresse(form.adresse());
            e.setInfos(form.infos());
            return mapper.toView(repository.save(e));
        });
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
