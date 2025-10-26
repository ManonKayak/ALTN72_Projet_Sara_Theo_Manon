package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.validation.constraints.NotBlank;

public record MemoireForm(
        @NotBlank String sujet,
        Float note,
        String commentaire,
        Integer dateSoutenance,
        Float noteSoutenance,
        String commentaireSoutenance
) {}
