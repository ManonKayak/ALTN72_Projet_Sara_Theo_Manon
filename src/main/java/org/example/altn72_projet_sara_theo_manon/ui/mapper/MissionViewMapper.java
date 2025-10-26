package org.example.altn72_projet_sara_theo_manon.ui.mapper;

import org.example.altn72_projet_sara_theo_manon.model.Mission;
import org.example.altn72_projet_sara_theo_manon.ui.viewmodel.MissionView;
import org.springframework.stereotype.Component;

@Component
public class MissionViewMapper {
    public MissionView toView(Mission m) {
        return new MissionView(
                m.getId(),
                m.getMotsCles(),
                m.getMetierCible(),
                m.getCommentaires()
        );
    }
}
