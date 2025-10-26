package org.example.altn72_projet_sara_theo_manon.ui.mapper;

import org.example.altn72_projet_sara_theo_manon.model.Entreprise;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.EntrepriseView;
import org.springframework.stereotype.Component;

@Component
public class EntrepriseViewMapper {
    public EntrepriseView toView(Entreprise e) {
        return new EntrepriseView(
                e.getId(),
                e.getRaisonSociale(),
                e.getAdresse(),
                e.getInfos()
        );
    }
}
