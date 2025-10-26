package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.ApprentiView;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.PageResponse;

import java.util.Optional;

public interface ApprentiReadService {
    PageResponse<ApprentiView> list(int page, int size, String q);
    Optional<ApprentiView> byId(Integer id);
}
