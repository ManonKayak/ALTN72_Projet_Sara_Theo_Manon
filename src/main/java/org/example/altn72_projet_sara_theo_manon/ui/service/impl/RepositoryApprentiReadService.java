package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.model.ApprentiRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.ApprentiViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.ApprentiReadService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.ApprentiView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryApprentiReadService implements ApprentiReadService {

    private final ApprentiRepository repository;
    private final ApprentiViewMapper mapper;

    public RepositoryApprentiReadService(ApprentiRepository repository, ApprentiViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<ApprentiView> list(int page, int size, String q) {
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.max(size, 1), Sort.by("id").descending());
        Page<Apprenti> p = (q == null || q.isBlank())
                ? repository.findAll(pageable)
                : repository.findByNomContainingIgnoreCaseOrPrenomContainingIgnoreCase(q, q, pageable);

        var content = p.getContent().stream().map(mapper::toView).toList();
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Override
    public Optional<ApprentiView> byId(Integer id) {
        return repository.findById(id).map(mapper::toView);
    }
}
