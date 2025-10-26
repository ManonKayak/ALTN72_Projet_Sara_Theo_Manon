package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EntrepriseForm(
        @NotBlank @Size(max = 255) String raisonSociale,
        @NotBlank @Size(max = 255) String adresse,
        @Size(max = 10000) String infos
) {}

