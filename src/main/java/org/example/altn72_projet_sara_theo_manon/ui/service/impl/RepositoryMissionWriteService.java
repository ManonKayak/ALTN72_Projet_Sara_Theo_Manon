package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.model.MissionRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.MissionViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.MissionForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.MissionWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MissionView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryMissionWriteService implements MissionWriteService {

    private final MissionRepository repository;
    private final MissionViewMapper mapper;

    public RepositoryMissionWriteService(MissionRepository repository, MissionViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MissionView create(MissionForm form) {
        var m = new Mission();
        m.setMotsCles(form.motsCles());
        m.setMetierCible(form.metierCible());
        m.setCommentaires(form.commentaires());
        return mapper.toView(repository.save(m));
    }

    @Override
    public Optional<MissionView> update(Integer id, MissionForm form) {
        return repository.findById(id).map(m -> {
            m.setMotsCles(form.motsCles());
            m.setMetierCible(form.metierCible());
            m.setCommentaires(form.commentaires());
            return mapper.toView(repository.save(m));
        });
    }

    @Override
    public boolean delete(Integer id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
