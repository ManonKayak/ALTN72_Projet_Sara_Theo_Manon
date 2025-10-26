package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MemoireView;

import java.util.Optional;

public interface MemoireWriteService {
    MemoireView create(MemoireForm form);
    Optional<MemoireView> update(Integer id, MemoireForm form);
    boolean delete(Integer id);
}
