package org.example.altn72_projet_sara_theo_manon.ui.service.impl;

import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.model.MissionRepository;
import org.example.altn72_projet_sara_theo_manon.ui.mapper.MissionViewMapper;
import org.example.altn72_projet_sara_theo_manon.ui.service.MissionReadService;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MissionView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepositoryMissionReadService implements MissionReadService {

    private final MissionRepository repository;
    private final MissionViewMapper mapper;

    public RepositoryMissionReadService(MissionRepository repository, MissionViewMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PageResponse<MissionView> list(int page, int size, String q) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<Mission> p = (q == null || q.isBlank())
                ? repository.findAll(pageable)
                : repository.findByMotsClesContainingIgnoreCaseOrMetierCibleContainingIgnoreCase(q, q, pageable);
        var content = p.getContent().stream().map(mapper::toView).toList();
        return new PageResponse<>(content, p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }

    @Override
    public Optional<MissionView> byId(Integer id) {
        return repository.findById(id).map(mapper::toView);
    }
}
