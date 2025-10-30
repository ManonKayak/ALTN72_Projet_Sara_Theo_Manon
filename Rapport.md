#  ASTA – Application de Suivi de Tutorats d’Apprentis
**Développement Full Stack Java | ALTN72 | S7 2025-26**

**Équipe :** Sara Tchinda • Théo Klein • Manon Gautier

---

## 1. Objectif

Remplacer un ancien fichier Excel de suivi de tutorats par une **application Web moderne** :  
**ASTA** (*Application de Suivi de Tutorats d’Apprentis*).

Cette application permet au **tuteur enseignant** de :
- Consulter les apprentis qu’il suit ;
- Gérer les informations relatives à leur entreprise, tuteur en entreprise, mission, mémoire et visites ;
- Rechercher, modifier, archiver et promouvoir les apprentis entre les années.

---

## 2. Expression du besoin (couverture)

Nous avons modélisé l’intégralité des données demandées dans le sujet :

### Entités principales
- **Apprenti :** programme, année académique, majeure, nom, prénom, mail, téléphone, remarques, niveau, archive.
- **Entreprise :** raison sociale, adresse, informations utiles.
- **Tuteur :** nom, prénom, poste, email, téléphone, remarques, entreprise.
- **Mission :** mots-clés, métier cible, commentaires.
- **Visite :** date, format (visio/présentiel), commentaire, apprenti, entreprise.
- **Mémoire :** sujet, note, commentaire, soutenance (date, note, commentaires).

### Champs supplémentaires proposés
- `archive` sur Apprenti (permet de filtrer les anciens élèves).
- Liens explicites entre entités (clé étrangère) pour navigation et cohérence.

---

##  3. Fonctionnalités principales réalisées

### 3.1 Authentification (Spring Security)
- Écran de connexion (form login).
- Message d’erreur si identifiants invalides.
- Une fois connecté : bandeau « Bonjour [Prénom] | Déconnectez-vous ».
- Déconnexion → retour à la page de login.

> Pour cette version, l’authentification est gérée en mémoire (`InMemoryUserDetailsManager`).

### 3.2 Tableau de bord (liste des apprentis)
- Affiche la liste des apprentis suivis par le tuteur pour l’année en cours.
- Message rouge si la liste est vide :
  > « La liste est vide. Ajoutez au moins un apprenti. »

### 3.3 Détails apprenti
Page détaillée regroupant toutes les informations d’un apprenti :
- Données personnelles,
- Entreprise,
- Mission,
- Tuteur entreprise,
- Visites,
- Mémoire et évaluations.

### 3.4 Modification des données
- Tous les champs sont modifiables depuis une page dédiée.
- Messages dynamiques :
    - « Modification réussie ! »
    - « Échec de la modification » avec erreurs explicites par champ.

### 3.5 Ajout d’un apprenti
- Création via formulaire.
- **Bonus prévu :** import CSV pour insertion en masse.

### 3.6 Changement d’année académique
- Les apprentis **I3** sont archivés.
- Les **I1 → I2** et **I2 → I3**.
- Les apprentis archivés ne sont plus visibles.
- Cette opération est gérée via un **service `@Transactional`**.

### 3.7 Recherche
- Critères possibles :
    - Nom de l’apprenti,
    - Entreprise,
    - Mot-clé de mission,
    - Année académique.
- Requête combinable et insensible à la casse (selon SGBD).

---

## 4. Exigences techniques

###  Stack / Outillage

| Élément | Choix |
|---|---|
| **Framework** | Spring Boot 3.5.6 |
| **Langage** | Java 17 |
| **IDE** | IntelliJ IDEA |
| **Vue** | Thymeleaf + Layout Dialect |
| **Sécurité** | Spring Security (Form Login) |
| **SGBD** | MariaDB 12.0.2 |
| **ORM** | Hibernate (Spring Data JPA) |
| **API Docs** | Swagger (springdoc-openapi) |
| **Build** | Maven |
| **Containerisation** | Docker + Docker Compose |

---

## 5. Documentation API (Swagger)

-  URL : **http://localhost:8081/swagger**
- Redirection automatique vers : **/swagger-ui/index.html**

**Endpoints documentés :**
- `/api/apprentis`
- `/api/entreprises`
- `/api/tuteurs`
- `/api/missions`
- `/api/memoires`
- `/api/visites`

> Tous les DTO sont annotés avec `@Valid` pour exposer les contraintes dans Swagger.

**Analyse critique :**  
Swagger a facilité la phase de test et de validation. C’est un outil technique utile pour l’API, mais il ne remplace pas une documentation métier claire. Nous avons privilégié **springdoc-openapi** pour sa simplicité d’intégration avec Spring Boot 3.x.

---

##  6. Déploiement (Docker)

###  Services
- **MariaDB**
    - Port : `3306`
    - Variables d’environnement : `MARIADB_ROOT_PASSWORD=verysecret`
    - Script d’initialisation : `Database/init.sql`
- **Application**
    - Port exposé : `8081:8080`
    - Dépend de MariaDB (`depends_on` + healthcheck)

### Lancement

```bash
docker-compose build --no-cache
docker-compose up
```

### Accès

- Application : http://localhost:8081
- Swagger : http://localhost:8081/swagger

---



##  7. Déploiement (Docker)

- **Connexion à l’application**
    - **Utilisateur :** tuteur
    - **Mot de passe :** password


- **Connexion à la base MariaDB**
    - **Utilisateur :** root
    - **Mot de passe :** verysecret

---

##  8. Persistance & transactions

### a) Requêtes JPA et JPQL

Exemples de requêtes personnalisées :

>@Query("SELECT a FROM Apprenti a WHERE " +
"(:q IS NULL OR LOWER(a.nom) LIKE LOWER(CONCAT('%', :q, '%'))) " +
"AND (:annee IS NULL OR a.anneeAcademique = :annee)")
List<Apprenti> search(@Param("q") String q, @Param("annee") Integer annee);

### b) Requête SQL pure (exigence du sujet)

>@Modifying
@Query(value = "UPDATE apprenti SET niveau = niveau + 1 WHERE archive = false AND niveau IN (1,2)", nativeQuery = true)
int promoteI1I2();

- **Analyse critique :**
    - Performance sur un traitement massif.
    - Moins portable (spécifique MariaDB).
    - Respecte la contrainte « 1 requête SQL standard max ».


### c) @Transactional

Utilisé uniquement dans la méthode de **changement d’année.**
Garantit la cohérence globale de la transaction (promotion + archivage atomiques).


---



##  9. Gestion des exceptions (Clean Code)

### a) Architecture

errors/

├─ NotFoundException.java

├─ BadRequestException.java

├─ GlobalRestExceptionHandler.java

└─ GlobalViewExceptionHandler.java

### b) Pourquoi @ControllerAdvice

- Centralise la gestion des erreurs REST et MVC.
- Évite les try/catch répétitifs.
- Uniformise les réponses JSON (ProblemDetail).

### c) Exemple de message renvoyé

>{
"type": "about:blank",
"title": "Ressource introuvable",
"status": 404,
"detail": "Tuteur id=42 introuvable",
"timestamp": "2025-10-29T23:55:41Z"
}


---



##  10. Lancer et tester

### Étapes

- 1.Démarrer Docker Desktop.
- 2.Cloner le projet.
- 3.Lancer les commandes :
```bash
docker-compose build --no-cache
docker-compose up
```
- 4.Ouvrir :
    - Interface : http://localhost:8081
    - Swagger : http://localhost:8081/swagger


---



##  11. Architecture du projet

    src/

        └─ main/
            ├─ java/org/example/altn72_projet_sara_theo_manon/
            │  ├─ api/              → Contrôleurs REST (Swagger)
            │  ├─ web/              → Contrôleurs MVC (Thymeleaf)
            │  ├─ model/            → Entités JPA & Interfaces Spring Data JPA
            │  ├─ service/          → Logique métier / @Transactional
            │  ├─ errors/           → Gestion d’exceptions globales
            │  ├─ login_security/   → Configuration de sécurité
            │  └─ config/           → Swagger / OpenAPI Config
            ├─ resources/
            │  ├─ application.yml
            │  ├─ templates/        → Vues Thymeleaf + pages d’erreur
            │  └─ static/           → CSS / JS
    ├─ Database/
    │   └─ init.sql
    ├─ docker-compose.yml
    ├─ Dockerfile
    └─ Rapport.md

---



##  12. Questions du rapport

### a) Sur quel aspect attirer l’attention du correcteur ?

Sur la gestion propre des exceptions, la clarté du Swagger, et le déploiement complet Dockerisé.

### b) Plus grande difficulté et solution

- **Difficulté :** faire fonctionner ensemble Spring Security, Swagger et Docker.

- **Solution :** configuration claire des routes publiques /swagger-ui/**, /v3/api-docs/** et script SQL d’initialisation fiable.

### c) Contribution de l’équipe

| Membre               | Contribution principale                                                     |
|----------------------|-----------------------------------------------------------------------------|
| **Théo**        | Modélisation BDD, Docker Compose, entités JPA, script SQL                   |
| **Sara**          | Création des vues Thymeleaf, structure du front et controllers MVC, rapport |
| **Manon**              | Authentification, API REST, gestion d’erreurs, Swagger, rapport                                                               |

---

### d) Trois points essentiels retenus

- 1.L’importance d’une **API documentée** et testable.
- 2.La **gestion centralisée des exceptions** pour un code maintenable.
- 3.La cohérence entre **backend, base et sécurité** via Docker.

---

### e) Fonctionnalités non terminées

- Liaison complète entre le **compte tuteur** et la base (actuellement InMemory).

- Import CSV à finaliser pour l’ajout massif d’apprentis.

---

### f) Principes SOLID respectés

- **S — Single Responsibility :** un service par entité métier.
- **O — Open/Closed :** endpoints extensibles via DTO.
- **L — Liskov Substitution :** services interchangeables, pas de violation.
- **I — Interface Segregation :** repositories ciblés et indépendants.
- **D — Dependency Inversion :** contrôleurs → services → repositories (dépendances abstraites).
