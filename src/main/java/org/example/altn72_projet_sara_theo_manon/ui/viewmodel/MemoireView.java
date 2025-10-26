package org.example.altn72_projet_sara_theo_manon.ui.viewmodel;

public record MemoireView(
        Integer id,
        String sujet,
        Float note,
        String commentaire,
        Integer dateSoutenance,
        Float noteSoutenance,
        String commentaireSoutenance
) {}
