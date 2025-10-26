package org.example.altn72_projet_sara_theo_manon.ui.mapper;

import org.example.altn72_projet_sara_theo_manon.model.Visite;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.VisiteView;
import org.springframework.stereotype.Component;

@Component
public class VisiteViewMapper {
    public VisiteView toView(Visite v) {
        return new VisiteView(
                v.getId(),
                v.getDate(),
                v.getFormat(),
                v.getCommentaire(),
                v.getApprenti() != null ? v.getApprenti().getPrenom() + " " + v.getApprenti().getNom() : "(aucun)",
                v.getEntreprise() != null ? v.getEntreprise().getRaisonSociale() : "(aucune)"
        );
    }
}
