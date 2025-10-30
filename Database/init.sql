CREATE DATABASE IF NOT EXISTS projectDB;

USE projectDB;

CREATE TABLE IF NOT EXISTS niveau (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS annee_academique (
    id INT AUTO_INCREMENT PRIMARY KEY,
    years TEXT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

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
    remarques TEXT,
    niveau INT NOT NULL,
    archive BOOLEAN NOT NULL,
    entreprise_id INT,
    mission_id INT NOT NULL,
    tuteur_id INT NOT NULL,
    memoire_id INT,
    CONSTRAINT fk_entreprise_id_apprenti FOREIGN KEY (entreprise_id) REFERENCES entreprise(id),
    CONSTRAINT fk_mission_id_apprenti FOREIGN KEY (mission_id) REFERENCES mission(id),
    CONSTRAINT fk_tuteur_id_apprenti FOREIGN KEY (tuteur_id) REFERENCES tuteur(id),
    CONSTRAINT fk_memoire_id_apprenti FOREIGN KEY (memoire_id) REFERENCES memoire(id),
    CONSTRAINT fk_annee_id_apprenti FOREIGN KEY (annee_academique) REFERENCES annee_academique(id)
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

INSERT INTO niveau (name) VALUES
                              ('I1'),
                              ('I2'),
                              ('I3');

INSERT INTO entreprise (raison_sociale, adresse, infos) VALUES
                                                            ('TechSolutions', '123 Rue de Paris, Paris', 'Entreprise spécialisée en développement logiciel.'),
                                                            ('InnoTech', '456 Avenue des Champs, Lyon', 'Innovation et conseil en technologies de l''information.'),
                                                            ('DevMasters', '789 Boulevard de la Tech, Toulouse', 'Experts en développement web et mobile.'),
                                                            ('CloudExperts', '101 Rue du Cloud, Bordeaux', 'Solutions cloud et infrastructure.'),
                                                            ('DataSystems', '202 Rue des Données, Marseille', 'Big Data et analyse de données.'),
                                                            ('SecureIT', '303 Rue de la Sécurité, Lille', 'Cybersécurité et protection des données.'),
                                                            ('WebCreators', '404 Rue du Web, Nantes', 'Création de sites web et applications.'),
                                                            ('AI Innovators', '505 Rue de l''Intelligence, Grenoble', 'Intelligence artificielle et machine learning.'),
                                                            ('NetworkPros', '606 Rue du Réseau, Strasbourg', 'Réseaux et télécommunications.'),
                                                            ('SoftEngine', '707 Rue du Logiciel, Rennes', 'Ingénierie logicielle et solutions sur mesure.');

INSERT INTO mission (mots_cles, metier_cible, commentaires) VALUES
                                                                ('Java, Spring, API', 'Développeur Backend', 'Développement d''une API REST pour un client bancaire.'),
                                                                ('React, JavaScript, Frontend', 'Développeur Frontend', 'Création d''une interface utilisateur pour une application SaaS.'),
                                                                ('Python, Data, Analyse', 'Data Analyst', 'Analyse de données clients pour un projet marketing.'),
                                                                ('DevOps, Cloud, AWS', 'Ingénieur DevOps', 'Automatisation du déploiement et gestion de l''infrastructure cloud.'),
                                                                ('C#, .NET, Application', 'Développeur .NET', 'Développement d''une application desktop pour la gestion de stocks.'),
                                                                ('Cybersécurité, Audit, Sécurité', 'Consultant Cybersécurité', 'Audit de sécurité pour une entreprise de e-commerce.'),
                                                                ('PHP, WordPress, CMS', 'Développeur Web', 'Personnalisation d''un site WordPress pour un client.'),
                                                                ('Machine Learning, Python, IA', 'Ingénieur IA', 'Développement d''un modèle de recommandation pour une plateforme.'),
                                                                ('Réseaux, Cisco, Administration', 'Administrateur Réseau', 'Configuration et maintenance d''un réseau d''entreprise.'),
                                                                ('Mobile, Flutter, Application', 'Développeur Mobile', 'Création d''une application mobile cross-platform.');

INSERT INTO tuteur (poste, nom, prenom, email, telephone, remarques, entreprise_id) VALUES
                                                                                        ('Ingénieur Logiciel', 'Martin', 'Pierre', 'pierre.martin@techsolutions.com', '0612345678', 'Expérience en gestion d''équipe.', 1),
                                                                                        ('Chef de Projet', 'Dupont', 'Marie', 'marie.dupont@innotech.fr', '0623456789', 'Expertise en méthodologies Agile.', 2),
                                                                                        ('Architecte Cloud', 'Bernard', 'Luc', 'luc.bernard@devmasters.com', '0634567890', 'Certifié AWS et Azure.', 3),
                                                                                        ('Data Scientist', 'Petit', 'Sophie', 'sophie.petit@datasystems.fr', '0645678901', 'Spécialiste en analyse prédictive.', 4),
                                                                                        ('Consultant Cybersécurité', 'Moreau', 'Thomas', 'thomas.moreau@secureit.com', '0656789012', 'Certifié CEH et CISSP.', 5),
                                                                                        ('Développeur Full Stack', 'Lefevre', 'Camille', 'camille.lefevre@webcreators.fr', '0667890123', 'Expérience en React et Node.js.', 6),
                                                                                        ('Ingénieur IA', 'Rousseau', 'Julien', 'julien.rousseau@aiinnovators.com', '0678901234', 'Doctorat en intelligence artificielle.', 7),
                                                                                        ('Administrateur Réseau', 'Durand', 'Élodie', 'elodie.durand@networkpros.fr', '0689012345', 'Expérience en gestion de réseaux complexes.', 8),
                                                                                        ('Technical Lead', 'Fournier', 'Antoine', 'antoine.fournier@softengine.com', '0690123456', '10 ans d''expérience en développement logiciel.', 9),
                                                                                        ('Product Owner', 'Girard', 'Laura', 'laura.girard@cloud experts.com', '0601234567', 'Expertise en gestion de produits tech.', 10);

INSERT INTO memoire (sujet, note, commentaire, date_soutenance, note_soutenance, commentaire_soutenance) VALUES
                                                                                                             ('Optimisation des requêtes SQL pour les applications web', 16.5, 'Très bon travail sur l''analyse des performances.', 20250615, 17.0, 'Présentation claire et réponse aux questions pertinentes.'),
                                                                                                             ('Développement d''une application mobile avec Flutter', 15.0, 'Application fonctionnelle et bien conçue.', 20250620, 16.0, 'Bon travail, quelques améliorations possibles sur l''UI.'),
                                                                                                             ('Sécurité des applications web : bonnes pratiques', 18.0, 'Excellent travail de recherche et d''implémentation.', 20250701, 18.5, 'Présentation professionnelle et technique.'),
                                                                                                             ('Analyse de données avec Python et Pandas', 14.5, 'Bonne analyse, mais manque de profondeur sur certains points.', 20250625, 15.0, 'Bonne défense, mais quelques erreurs dans les résultats.'),
                                                                                                             ('Automatisation des tests avec Selenium', 17.5, 'Très bonne implémentation des tests automatisés.', 20250705, 18.0, 'Présentation très technique et bien maîtrisée.'),
                                                                                                             ('Conception d''une API REST avec Spring Boot', 16.0, 'API bien structurée et documentée.', 20250630, 16.5, 'Réponses précises aux questions du jury.'),
                                                                                                             ('Machine Learning pour la recommandation de produits', 19.0, 'Travail exceptionnel sur les algorithmes de recommandation.', 20250710, 19.5, 'Présentation claire et résultats impressionnants.'),
                                                                                                             ('Gestion de projet Agile : étude de cas', 15.5, 'Bonne analyse, mais manque de détails sur les outils utilisés.', 20250715, 16.0, 'Bonne défense, mais quelques points à approfondir.'),
                                                                                                             ('Déploiement continu avec Docker et Kubernetes', 18.5, 'Excellent travail sur l''automatisation du déploiement.', 20250720, 19.0, 'Présentation technique et bien préparée.'),
                                                                                                             ('Développement d''un chatbot avec NLP', 17.0, 'Chatbot fonctionnel et bien entraîné.', 20250725, 17.5, 'Bonne défense, mais quelques limites identifiées.');

INSERT INTO apprenti (annee_academique, majeure, nom, prenom, mail, telephone, remarques, niveau, archive, entreprise_id, mission_id, tuteur_id, memoire_id) VALUES
                                                                                                                                                                 (2025, 1, 'Leroy', 'Hugo', 'hugo.leroy@etudiant.fr', '0712345678', 'Motivé et autonome.', 1, FALSE, 1, 1, 1, 1),
                                                                                                                                                                 (2025, 2, 'Durand', 'Emma', 'emma.durand@etudiant.fr', '0723456789', 'Très bonne compréhension des concepts.', 2, FALSE, 2, 2, 2, 2),
                                                                                                                                                                 (2025, 1, 'Martin', 'Lucas', 'lucas.martin@etudiant.fr', '0734567890', 'Bon travail d''équipe.', 1, FALSE, 3, 3, 3, 3),
                                                                                                                                                                 (2025, 3, 'Bernard', 'Chloé', 'chloe.bernard@etudiant.fr', '0745678901', 'Excellente analyse technique.', 3, FALSE, 4, 4, 4, 4),
                                                                                                                                                                 (2025, 2, 'Petit', 'Thomas', 'thomas.petit@etudiant.fr', '0756789012', 'Très impliqué dans les projets.', 2, FALSE, 5, 5, 5, 5),
                                                                                                                                                                 (2025, 1, 'Moreau', 'Léa', 'lea.moreau@etudiant.fr', '0767890123', 'Bonne curiosité technique.', 1, FALSE, 6, 6, 6, 6),
                                                                                                                                                                 (2025, 3, 'Lefevre', 'Antoine', 'antoine.lefevre@etudiant.fr', '0778901234', 'Autonome et rigoureux.', 3, FALSE, 7, 7, 7, 7),
                                                                                                                                                                 (2025, 2, 'Rousseau', 'Camille', 'camille.rousseau@etudiant.fr', '0789012345', 'Très bonne communication.', 2, FALSE, 8, 8, 8, 8),
                                                                                                                                                                 (2025, 1, 'Durand', 'Jules', 'jules.durand@etudiant.fr', '0790123456', 'Motivé et créatif.', 1, FALSE, 9, 9, 9, 9),
                                                                                                                                                                 (2025, 3, 'Fournier', 'Manon', 'manon.fournier@etudiant.fr', '0701234567', 'Excellente gestion de projet.', 3, FALSE, 10, 10, 10, 10);

INSERT INTO visite (date, format, commentaire, apprenti_id, entreprise_id) VALUES
                                                                               (20250510, 1, 'Première visite, bonne intégration.', 1, 1),
                                                                               (20250515, 2, 'Visite de suivi, projet en bonne voie.', 2, 2),
                                                                               (20250520, 1, 'Visite technique, bonnes questions posées.', 3, 3),
                                                                               (20250525, 2, 'Visite de mi-parcours, tout se passe bien.', 4, 4),
                                                                               (20250601, 1, 'Visite pour valider les objectifs.', 5, 5),
                                                                               (20250605, 2, 'Visite de suivi, projet avancé.', 6, 6),
                                                                               (20250610, 1, 'Visite technique, bonnes pratiques observées.', 7, 7),
                                                                               (20250615, 2, 'Visite de fin de mission, résultats satisfaisants.', 8, 8),
                                                                               (20250620, 1, 'Visite pour préparer la soutenance.', 9, 9),
                                                                               (20250625, 2, 'Visite finale, apprenti très impliqué.', 10, 10);