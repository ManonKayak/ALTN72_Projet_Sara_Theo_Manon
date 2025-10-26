package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record VisiteForm(
        @NotNull Integer date,
        Integer format,
        String commentaire,
        @NotNull Integer apprentiId,
        @NotNull Integer entrepriseId
) {}
