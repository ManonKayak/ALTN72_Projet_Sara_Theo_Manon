package org.example.altn72_projet_sara_theo_manon.ui.viewmodel;

public record ApprentiView(
        Integer id,
        Integer anneeAcademique,
        Integer majeure,
        String nomComplet,
        String mail,
        String telephone,
        Integer niveau,
        Boolean archive
) {}
