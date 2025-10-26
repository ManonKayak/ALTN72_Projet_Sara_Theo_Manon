package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.validation.constraints.NotBlank;

public record MissionForm(
        @NotBlank String motsCles,
        @NotBlank String metierCible,
        @NotBlank String commentaires
) {}
