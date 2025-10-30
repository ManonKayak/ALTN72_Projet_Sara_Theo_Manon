# Projet final Java fullStack
## Par Sara Tchinda, Théo Klein, Manon Gautier

---

## Identifiants de test
Connexion à la base de donnée :
- login : root
- mot de passe : verysecret
Connexion sur l'application :
- tuteur : tuteur
- mot de passe : password

---

## Outillage
Nous avons choisi d'utiliser InteliJ IDEA comme IDE de notre application et MariaDB (version : 12.0.2-MariaDB-ubu2404) comme SGBD.

---

## Pour lancer l'application
- **Prérequis** :
    - Avoir Docker Deskstop installé.

- **Lancement de l'application en local** :
    - Avant de pouvoir lancer l'application, assurez-vous de démarrer Docker Desktop.
    - Puis lancer ces commandes à la racine du projet :
      - docker-compose build --no-cache
      - docker-compose up
    - Enfin, ouvrez votre navigateur et allez à l'adresse suivante : http://localhost:8081

- **Lancement de l'application depuis **:
    -  Allez sur : 

---

## Questions-réponses

### a) Sur quel aspect de votre travail souhaitez-vous attirer l'attention du correcteur ?
[Expliquez ici sur quel aspect de votre travail, vous souhaitez attirer l'attention du correcteur.]

### b) Quelle est la plus grande difficulté que vous avez rencontrée ? Comment avez-vous géré/solutionné/contourné cette difficulté ?
- **Difficulté** : 
- **Solution/Gestion** : [Expliquez comment vous l'avez résolue ou contournée.]

### c) Quelle a été la contribution de chaque membre de l'équipe ?
- **Théo** : Création de la base de données, du container Docker et des classes entités.
- **Sara** : Création des différentes pages et de l'aspect visuel ainsi que la structure des services et controllers.
- **Manon** : Ajout de la page de connexion, completion et débogage des services et controllers, rédaction du rapport.

### d) Si vous deviez retenir trois points de ce cours en général et de ce projet en particulier, quels seraient ces trois points ?
1. [Premier point]
2. [Deuxième point]
3. [Troisième point]

### e) Les fonctionnalités que vous n'avez pas eues le temps de mettre en œuvre et pourquoi.
- Nous n'avons pas réussi à lier la connection d'un tuteur avec la base de donnée. Pour le moment, la connection se fait avec un email et un mot de passe par défaut ("tuteur" et "password").
- **Fonctionnalité 2** : [Décrivez la fonctionnalité et pourquoi

### f) À quel niveau, dans votre projet, avez-vous réussi à respecter entièrement ou partiellement les principes SOLID ?
- **Principe S** : [Expliquez comment vous avez respecté ce principe.]