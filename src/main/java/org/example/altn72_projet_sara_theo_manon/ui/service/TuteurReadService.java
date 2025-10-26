package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.TuteurView;

import java.util.Optional;

public interface TuteurReadService {
    PageResponse<TuteurView> list(int page, int size, String q);
    Optional<TuteurView> byId(Integer id);
}
