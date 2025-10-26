package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.TuteurViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.TuteurForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.TuteurWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.TuteurView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryTuteurWriteService implements TuteurWriteService {

    private final TuteurRepository repository;
    private final TuteurViewMapper mapper;

    public RepositoryTuteurWriteService(TuteurRepository repository, TuteurViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TuteurView create(TuteurForm form) {
        var t = new Tuteur();
        mapFormToEntity(form, t);
        return mapper.toView(repository.save(t));
    }

    @Override
    public Optional<TuteurView> update(Integer id, TuteurForm form) {
        return repository.findById(id).map(t -> {
            mapFormToEntity(form, t);
            return mapper.toView(repository.save(t));
        });
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    private void mapFormToEntity(TuteurForm form, Tuteur t) {
        t.setNom(form.nom());
        t.setPrenom(form.prenom());
        t.setEmail(form.email());
        t.setTelephone(form.telephone());
        t.setPoste(form.poste());
        t.setRemarques(form.remarques());
        if (form.entrepriseId() != null) {
            var e = new Entreprise();
            e.setId(form.entrepriseId());
            t.setEntreprise(e);
        } else {
            t.setEntreprise(null);
        }
    }
}
