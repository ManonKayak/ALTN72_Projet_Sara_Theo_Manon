package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.TuteurView;

import java.util.Optional;

public interface TuteurWriteService {
    TuteurView create(TuteurForm form);
    Optional<TuteurView> update(Integer id, TuteurForm form);
    boolean delete(Integer id);
}
