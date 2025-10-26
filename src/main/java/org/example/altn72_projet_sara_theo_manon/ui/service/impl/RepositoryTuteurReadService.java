package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.model.TuteurRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.TuteurViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.TuteurReadService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.TuteurView;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryTuteurReadService implements TuteurReadService {

    private final TuteurRepository repository;
    private final TuteurViewMapper mapper;

    public RepositoryTuteurReadService(TuteurRepository repository, TuteurViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<TuteurView> list(int page, int size, String q) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by("id").descending());
        Page<Tuteur> p = (q == null || q.isBlank())
                ? repository.findAll(pageable)
                : repository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(q, q, pageable);

        var content = p.getContent().stream().map(mapper::toView).toList();
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Override
    public Optional<TuteurView> byId(Integer id) {
        return repository.findById(id).map(mapper::toView);
    }
}
