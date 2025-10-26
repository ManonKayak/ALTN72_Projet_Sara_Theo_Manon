package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.EntrepriseView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;

import java.util.Optional;

public interface EntrepriseReadService {
    PageResponse<EntrepriseView> list(int page, int size, String q);
    Optional<EntrepriseView> byId(Integer id);
}
