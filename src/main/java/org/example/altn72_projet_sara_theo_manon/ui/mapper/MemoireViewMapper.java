package org.example.altn72_projet_sara_theo_manon.ui.mapper;

import org.example.altn72_projet_sara_theo_manon.model.Memoire;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MemoireView;
import org.springframework.stereotype.Component;

@Component
public class MemoireViewMapper {
    public MemoireView toView(Memoire m) {
        return new MemoireView(
                m.getId(),
                m.getSujet(),
                m.getNote(),
                m.getCommentaire(),
                m.getDateSoutenance(),
                m.getNoteSoutenance(),
                m.getCommentaireSoutenance()
        );
    }
}
