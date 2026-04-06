| Elément                                                                            | Description                                                                                                             |
| ---------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                                                       | Afficher les tâches par statut                                                                                          |
| **But / Objectif**                                                                 | Permettre à un utilisateur authentifié d’organiser visuellement ses tâches en trois colonnes (ToDo, In Progress, Done). |
| **Acteur principal**                                                               | Utilisateur authentifié                                                                                                 |
| **Acteur secondaire**                                                              | Base de données                                                                                                         |
| **Préconditions**                                                                  | L’utilisateur est authentifié. Au moins une tâche existe dans la base.                                                  |
| **Scénario principal**                                                             |                                                                                                                         |
| 1. L’utilisateur accède à son espace de gestion des tâches.                        | Le système affiche les tâches triées par statut (ToDo, In Progress, Done).                                              |
| 2. L’utilisateur peut glisser-déposer une tâche d’une colonne à l’autre.           | Le système met à jour le statut de la tâche dans la base de données.                                                    |
| 3. Le système affiche un message de confirmation pour chaque changement de statut. | La tâche apparaît dans la nouvelle colonne.                                                                             |

[Retour](/.UML/FunctionalSpecifications.md)
