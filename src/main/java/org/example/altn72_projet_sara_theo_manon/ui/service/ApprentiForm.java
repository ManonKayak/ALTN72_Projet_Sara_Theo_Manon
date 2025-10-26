package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.validation.constraints.*;

public record ApprentiForm(
        @NotNull Integer anneeAcademique,
        @NotNull Integer majeure,
        @NotBlank String nom,
        @NotBlank String prenom,
        @Email @NotBlank String mail,
        @NotBlank String telephone,
        @NotNull Integer niveau,
        @NotNull Boolean archive
) {}
