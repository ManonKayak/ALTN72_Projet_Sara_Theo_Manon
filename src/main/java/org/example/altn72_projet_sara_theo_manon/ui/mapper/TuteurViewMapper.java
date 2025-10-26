package org.example.altn72_projet_sara_theo_manon.ui.mapper;

import org.example.altn72_projet_sara_theo_manon.model.Tuteur;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.TuteurView;
import org.springframework.stereotype.Component;

@Component
public class TuteurViewMapper {
    public TuteurView toView(Tuteur t) {
        return new TuteurView(
                t.getId(),
                t.getPrenom() + " " + t.getNom(),
                t.getEmail(),
                t.getTelephone(),
                t.getPoste(),
                t.getEntreprise() != null ? t.getEntreprise().getRaisonSociale() : "(non renseignée)",
                t.getRemarques()
        );
    }
}
