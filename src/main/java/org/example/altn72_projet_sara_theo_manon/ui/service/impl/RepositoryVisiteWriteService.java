package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.*;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.VisiteViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.VisiteForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.VisiteWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.VisiteView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryVisiteWriteService implements VisiteWriteService {

    private final VisiteRepository repository;
    private final VisiteViewMapper mapper;

    public RepositoryVisiteWriteService(VisiteRepository repository, VisiteViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public VisiteView create(VisiteForm form) {
        var v = new Visite();
        mapFormToEntity(form, v);
        return mapper.toView(repository.save(v));
    }

    @Override
    public Optional<VisiteView> update(Integer id, VisiteForm form) {
        return repository.findById(id).map(v -> {
            mapFormToEntity(form, v);
            return mapper.toView(repository.save(v));
        });
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    private void mapFormToEntity(VisiteForm form, Visite v) {
        v.setDate(form.date());
        v.setFormat(form.format());
        v.setCommentaire(form.commentaire());

        var apprenti = new Apprenti();
        apprenti.setId(form.apprentiId());
        v.setApprenti(apprenti);

        var entreprise = new Entreprise();
        entreprise.setId(form.entrepriseId());
        v.setEntreprise(entreprise);
    }
}
