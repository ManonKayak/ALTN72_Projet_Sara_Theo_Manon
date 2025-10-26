package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.ApprentiView;

import java.util.Optional;

public interface ApprentiWriteService {
    ApprentiView create(ApprentiForm form);
    Optional<ApprentiView> update(Integer id, ApprentiForm form);
    boolean delete(Integer id);
}
