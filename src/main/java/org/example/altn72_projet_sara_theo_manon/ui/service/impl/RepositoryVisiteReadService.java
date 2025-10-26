package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.model.VisiteRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.VisiteViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.VisiteReadService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.VisiteView;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryVisiteReadService implements VisiteReadService {

    private final VisiteRepository repository;
    private final VisiteViewMapper mapper;

    public RepositoryVisiteReadService(VisiteRepository repository, VisiteViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<VisiteView> list(int page, int size, String q) {
        Pageable pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), Sort.by("id").descending());
        Page<Visite> p = (q == null || q.isBlank())
                ? repository.findAll(pageable)
                : repository.findByCommentaireContainingIgnoreCase(q, pageable);
        var content = p.getContent().stream().map(mapper::toView).toList();
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Override
    public Optional<VisiteView> byId(Integer id) {
        return repository.findById(id).map(mapper::toView);
    }
}
