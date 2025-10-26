package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.ApprentiRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.ApprentiViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.ApprentiForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.ApprentiWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.ApprentiView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryApprentiWriteService implements ApprentiWriteService {

    private final ApprentiRepository repository;
    private final ApprentiViewMapper mapper;

    public RepositoryApprentiWriteService(ApprentiRepository repository, ApprentiViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public ApprentiView create(ApprentiForm form) {
        var a = new Apprenti();
        mapFormToEntity(form, a);
        a.setRemarques(""); // défaut
        return mapper.toView(repository.save(a));
    }

    @Override
    public Optional<ApprentiView> update(Integer id, ApprentiForm form) {
        return repository.findById(id).map(a -> {
            mapFormToEntity(form, a);
            return mapper.toView(repository.save(a));
        });
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    private static void mapFormToEntity(ApprentiForm f, Apprenti a) {
        a.setAnneeAcademique(f.anneeAcademique());
        a.setMajeure(f.majeure());
        a.setNom(f.nom());
        a.setPrenom(f.prenom());
        a.setMail(f.mail());
        a.setTelephone(f.telephone());
        a.setNiveau(f.niveau());
        a.setArchive(f.archive());
    }
}
