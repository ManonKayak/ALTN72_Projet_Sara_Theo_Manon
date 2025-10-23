CREATE DATABASE IF NOT EXISTS projectDB;

USE projectDB;

CREATE TABLE IF NOT EXISTS entreprise (
    id INT AUTO_INCREMENT PRIMARY KEY,
    raison_sociale TEXT NOT NULL,
    adresse TEXT NOT NULL,
    infos TEXT
);

CREATE TABLE IF NOT EXISTS mission (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mots_cles TEXT NOT NULL,
    metier_cible TEXT NOT NULL,
    commentaires TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tuteur (
    id INT AUTO_INCREMENT PRIMARY KEY,
    poste TEXT NOT NULL,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    email TEXT NOT NULL,
    telephone TEXT NOT NULL,
    remarques TEXT NOT NULL,
    entreprise_id INT,
    CONSTRAINT fk_entreprise_id FOREIGN KEY (entreprise_id) REFERENCES entreprise(id)
);

CREATE TABLE IF NOT EXISTS memoire (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sujet TEXT NOT NULL,
    note FLOAT,
    commentaire TEXT,
    date_soutenance INT,
    note_soutenance FLOAT,
    commentaire_soutenance TEXT
);

CREATE TABLE IF NOT EXISTS apprenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    annee_academique INT NOT NULL,
    majeure INT NOT NULL,
    nom TEXT NOT NULL,
    prenom TEXT NOT NULL,
    mail TEXT NOT NULL,
    telephone TEXT NOT NULL,
    remarques TEXT NOT NULL,
    niveau INT NOT NULL,
    archive BOOLEAN NOT NULL,
    entreprise_id INT NOT NULL,
    mission_id INT NOT NULL,
    tuteur_id INT NOT NULL,
    memoire_id INT,
    CONSTRAINT fk_entreprise_id_apprenti FOREIGN KEY (entreprise_id) REFERENCES entreprise(id),
    CONSTRAINT fk_mission_id_apprenti FOREIGN KEY (mission_id) REFERENCES mission(id),
    CONSTRAINT fk_tuteur_id_apprenti FOREIGN KEY (tuteur_id) REFERENCES tuteur(id),
    CONSTRAINT fk_memoire_id_apprenti FOREIGN KEY (memoire_id) REFERENCES memoire(id)
);

CREATE TABLE IF NOT EXISTS visite (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date INT NOT NULL,
    format INT,
    commentaire TEXT,
    apprenti_id INT NOT NULL,
    entreprise_id INT NOT NULL,
    CONSTRAINT fk_apprenti_id_visite FOREIGN KEY (apprenti_id) REFERENCES apprenti(id),
    CONSTRAINT fk_entreprise_id_visite FOREIGN KEY (entreprise_id) REFERENCES entreprise(id)
);

