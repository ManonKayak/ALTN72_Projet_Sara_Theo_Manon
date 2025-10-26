package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.EntrepriseView;

import java.util.Optional;

public interface EntrepriseWriteService {
    EntrepriseView create(EntrepriseForm form);
    Optional<EntrepriseView> update(Integer id, EntrepriseForm form);
    boolean delete(Integer id);
}
