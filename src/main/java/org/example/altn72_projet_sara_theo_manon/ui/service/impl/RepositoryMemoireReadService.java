package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.model.MemoireRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.MemoireViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.MemoireReadService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MemoireView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryMemoireReadService implements MemoireReadService {

    private final MemoireRepository repository;
    private final MemoireViewMapper mapper;

    public RepositoryMemoireReadService(MemoireRepository repository, MemoireViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<MemoireView> list(int page, int size, String q) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), Sort.by("id").descending());
        Page<Memoire> p = (q == null || q.isBlank())
                ? repository.findAll(pageable)
                : repository.findBySujetContainingIgnoreCaseOrCommentaireContainingIgnoreCase(q, q, pageable);

        var content = p.getContent().stream().map(mapper::toView).toList();
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Override
    public Optional<MemoireView> byId(Integer id) {
        return repository.findById(id).map(mapper::toView);
    }
}
