package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.model.EntrepriseRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.EntrepriseViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.EntrepriseReadService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.EntrepriseView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryEntrepriseReadService implements EntrepriseReadService {

    private final EntrepriseRepository repository;
    private final EntrepriseViewMapper mapper;

    public RepositoryEntrepriseReadService(EntrepriseRepository repository, EntrepriseViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<EntrepriseView> list(int page, int size, String q) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by("id").descending());
        Page<Entreprise> p = (q == null || q.isBlank())
                ? repository.findAll(pageable)
                : repository.findByRaisonSocialeContainingIgnoreCaseOrAdresseContainingIgnoreCase(q, q, pageable);

        var content = p.getContent().stream().map(mapper::toView).toList();
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Override
    public Optional<EntrepriseView> byId(Integer id) {
        return repository.findById(id).map(mapper::toView);
    }
}
