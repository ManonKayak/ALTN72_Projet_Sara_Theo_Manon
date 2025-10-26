package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MissionView;

import java.util.Optional;

public interface MissionWriteService {
    MissionView create(MissionForm form);
    Optional<MissionView> update(Integer id, MissionForm form);
    boolean delete(Integer id);
}
