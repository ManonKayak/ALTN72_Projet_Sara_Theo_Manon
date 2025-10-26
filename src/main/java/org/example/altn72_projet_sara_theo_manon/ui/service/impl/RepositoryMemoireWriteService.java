package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.model.MemoireRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.MemoireViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.MemoireForm;
import org.example.altn72_projet_sara_theo_manon.ui.service.MemoireWriteService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MemoireView;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryMemoireWriteService implements MemoireWriteService {

    private final MemoireRepository repository;
    private final MemoireViewMapper mapper;

    public RepositoryMemoireWriteService(MemoireRepository repository, MemoireViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public MemoireView create(MemoireForm form) {
        var m = new Memoire();
        m.setSujet(form.sujet());
        m.setNote(form.note());
        m.setCommentaire(form.commentaire());
        m.setDateSoutenance(form.dateSoutenance());
        m.setNoteSoutenance(form.noteSoutenance());
        m.setCommentaireSoutenance(form.commentaireSoutenance());
        return mapper.toView(repository.save(m));
    }

    @Override
    public Optional<MemoireView> update(Integer id, MemoireForm form) {
        return repository.findById(id).map(m -> {
            m.setSujet(form.sujet());
            m.setNote(form.note());
            m.setCommentaire(form.commentaire());
            m.setDateSoutenance(form.dateSoutenance());
            m.setNoteSoutenance(form.noteSoutenance());
            m.setCommentaireSoutenance(form.commentaireSoutenance());
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
