package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.VisiteView;

import java.util.Optional;

public interface VisiteReadService {
    PageResponse<VisiteView> list(int page, int size, String q);
    Optional<VisiteView> byId(Integer id);
}
