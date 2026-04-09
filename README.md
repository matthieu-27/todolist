# TodoList - Application de gestion de tâches

## Prérequis

- Java 21 ou supérieur
- Maven 3.6.3 ou supérieur
- Git
- MariaDB ou MySQL

## Installation

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/votre-utilisateur/todolist.git
   ```
2. Configurer la base de données :

- Créer une base de données MariaDB/MySQL
- Mettre à jour les informations de connexion dans src/main/resources/application.properties

3.  Construire le projet :

    ```
    mvn clean install
    ```

4.  Démarrer l'application :

    ```
    mvn spring-boot:run
    ```

    Lancement

5.  Accéder à l'application dans votre navigateur :

    ```
    http://localhost:8080/
    ```

6.  Se connecter avec l'utilisateur par défaut :
    - Nom d'utilisateur : john
    - Mot de passe : password123
      Fonctionnalités
    - Gestion des tâches :
    - Création, modification et suppression de tâches
    - Organisation des tâches par statut (TODO, DOING, DONE)
    - Affichage des tâches par semaine

    Structure du Projet:

        todolist/
        ├── src/
        │   ├── main/
        │   │   ├── java/fr/fms/todolist/
        │   │   │   ├── config/          # Configuration Spring
        │   │   │   ├── controller/     # Contrôleurs
        │   │   │   ├── dao/            # Repositories
        │   │   │   ├── entities/       # Entités JPA
        │   │   │   ├── enums/          # Énumérations
        │   │   │   └── TodolistApplication.java
        │   │   └── resources/
        │   │       ├── static/         # Fichiers statiques
        │   │       └── templates/      # Templates Thymeleaf
        │   └── test/                  # Tests
        ├── pom.xml                     # Configuration Maven
        └── README.md                   # Documentation
