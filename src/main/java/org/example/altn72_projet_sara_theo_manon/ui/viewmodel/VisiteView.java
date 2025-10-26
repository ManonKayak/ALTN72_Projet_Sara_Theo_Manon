package org.example.altn72_projet_sara_theo_manon.ui.viewmodel;

public record VisiteView(
        Integer id,
        Integer date,
        Integer format,
        String commentaire,
        String apprentiNom,
        String entrepriseNom
) {}
