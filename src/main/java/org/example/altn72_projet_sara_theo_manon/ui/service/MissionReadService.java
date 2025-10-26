package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MissionView;

import java.util.Optional;

public interface MissionReadService {
    PageResponse<MissionView> list(int page, int size, String q);
    Optional<MissionView> byId(Integer id);
}
