package org.example.altn72_projet_sara_theo_manon.ui.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TuteurForm(
        @NotBlank @Size(max = 100) String prenom,
        @NotBlank @Size(max = 100) String nom,
        @NotBlank String poste,
        @Email @NotBlank String email,
        @NotBlank String telephone,
        String remarques,
        Integer entrepriseId
) {}
