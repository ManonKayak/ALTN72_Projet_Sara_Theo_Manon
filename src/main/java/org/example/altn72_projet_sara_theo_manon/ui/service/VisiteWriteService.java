package org.example.altn72_projet_sara_theo_manon.ui.service;

import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.VisiteView;
import java.util.Optional;

public interface VisiteWriteService {
    VisiteView create(VisiteForm form);
    Optional<VisiteView> update(Integer id, VisiteForm form);
    boolean delete(Integer id);
}
