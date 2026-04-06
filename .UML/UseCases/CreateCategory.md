| Elément                                              | Description                                                                                     |
| ---------------------------------------------------- | ----------------------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                         | Créer une catégorie                                                                             |
| **But / Objectif**                                   | Permettre à un utilisateur authentifié de créer une nouvelle catégorie pour classer ses tâches. |
| **Acteur principal**                                 | Utilisateur authentifié                                                                         |
| **Acteur secondaire**                                | Base de données                                                                                 |
| **Préconditions**                                    | L’utilisateur est authentifié. Le module de gestion des catégories est accessible.              |
| **Scénario principal**                               |                                                                                                 |
| 1. L’utilisateur clique sur "Gérer les catégories".  | Le système affiche une liste des catégories existantes.                                         |
| 2. L’utilisateur clique sur "Ajouter une catégorie". | Le système affiche un formulaire vide.                                                          |
| 3. L’utilisateur saisit le nom de la catégorie.      | Le système valide le nom.                                                                       |
| 4. L’utilisateur valide la création.                 | Le système enregistre la catégorie dans la base de données.                                     |
| 5. Le système affiche un message de confirmation.    | La nouvelle catégorie apparaît dans la liste.                                                   |
| **Scénario secondaire 1**                            |                                                                                                 |
| 1. Le nom de la catégorie existe déjà.               | Le système affiche un message d’erreur : "Cette catégorie existe déjà."                         |

[Retour](/.UML/FunctionalSpecifications.md)
