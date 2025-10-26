package org.example.altn72_projet_sara_theo_manon.ui.mapper;

import org.example.altn72_projet_sara_theo_manon.model.Apprenti;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.ApprentiView;
import org.springframework.stereotype.Component;

@Component
public class ApprentiViewMapper {
    public ApprentiView toView(Apprenti a) {
        String nomComplet = (a.getPrenom() != null ? a.getPrenom() : "") +
                " " + (a.getNom() != null ? a.getNom() : "");
        return new ApprentiView(
                a.getId(),
                a.getAnneeAcademique(),
                a.getMajeure(),
                nomComplet.trim(),
                a.getMail(),
                a.getTelephone(),
                a.getNiveau(),
                Boolean.TRUE.equals(a.getArchive())
        );
    }
}
