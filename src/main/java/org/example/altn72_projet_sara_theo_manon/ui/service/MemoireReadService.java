package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MemoireView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;

import java.util.Optional;

public interface MemoireReadService {
    PageResponse<MemoireView> list(int page, int size, String q);
    Optional<MemoireView> byId(Integer id);
}
