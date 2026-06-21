# Architecture logicielle — ALTN72 Projet Sara Théo Manon

Application web Spring Boot de gestion des apprentis EFREI (suivi des entreprises, tuteurs, missions, mémoires et visites).

---

## Vue d'ensemble — Architecture en couches

```mermaid
graph TD
    subgraph Client["Navigateur"]
        VUE["Vues Thymeleaf"]
    end

    subgraph Securite["Sécurité"]
        CONFIG_SEC["Configuration de sécurité"]
        AUTH_HANDLER["Gestionnaire d'authentification"]
    end

    subgraph Web["Couche Web"]
        CTRL_UI["Contrôleurs UI"]
        CTRL_API["Contrôleurs REST"]
        CTRL_DOC["Contrôleurs Documentation"]
    end

    subgraph Services["Couche Service"]
        SVC["Services métier"]
    end

    subgraph Donnees["Couche Données"]
        ENTITES["Entités JPA"]
        REPOS["Repositories Spring Data"]
    end

    subgraph Erreurs["Gestion des erreurs"]
        EX_HANDLER_REST["Gestionnaire d'exceptions REST"]
        EX_HANDLER_VIEW["Gestionnaire d'exceptions UI"]
        EXCEPTIONS["Exceptions métier"]
    end

    subgraph Infra["Infrastructure"]
        DB[("Base de données\nMariaDB / PostgreSQL")]
        DB_TEST[("Base de données\nH2 - tests")]
    end

    VUE -->|HTTP| Securite
    Securite --> CTRL_UI
    Securite --> CTRL_API
    CTRL_UI --> SVC
    CTRL_API --> REPOS
    SVC --> REPOS
    REPOS --> ENTITES
    ENTITES --> DB
    ENTITES -.->|tests| DB_TEST
    CTRL_API -.->|erreurs| EX_HANDLER_REST
    CTRL_UI -.->|erreurs| EX_HANDLER_VIEW
    EX_HANDLER_REST --> EXCEPTIONS
    EX_HANDLER_VIEW --> EXCEPTIONS
    CTRL_DOC --> CTRL_API
```

---

## Stack technique

| Composant | Technologie |
|-----------|-------------|
| Framework backend | Spring Boot 3.5.6 |
| Langage | Java 17 |
| Persistance | Spring Data JPA + Hibernate |
| Base de données (prod) | MariaDB / PostgreSQL |
| Base de données (test) | H2 |
| Sécurité | Spring Security (InMemory) |
| Template engine | Thymeleaf + Layout Dialect |
| Documentation API | Springdoc OpenAPI / Swagger UI |
| Conteneurisation | Docker + Docker Compose |
| Build | Maven |
