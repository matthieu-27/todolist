| Elément                                               | Description                                                                      |
| ----------------------------------------------------- | -------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                          | Supprimer une tâche                                                              |
| **But / Objectif**                                    | Permettre à un utilisateur authentifié de supprimer définitivement une tâche.    |
| **Acteur principal**                                  | Utilisateur authentifié                                                          |
| **Acteur secondaire**                                 | Base de données                                                                  |
| **Préconditions**                                     | L’utilisateur est authentifié. L’utilisateur a au moins une tâche dans sa liste. |
| **Scénario principal**                                |                                                                                  |
| 1. L’utilisateur sélectionne une tâche dans sa liste. | Le système affiche les détails de la tâche.                                      |
| 2. L’utilisateur clique sur "Supprimer".              | Le système demande une confirmation.                                             |
| 3. L’utilisateur confirme la suppression.             | Le système supprime la tâche de la base de données.                              |
| 4. Le système affiche un message de confirmation.     | La tâche n’apparaît plus dans la liste.                                          |
| **Scénario secondaire 1**                             |                                                                                  |
| 1. L’utilisateur annule la suppression.               | Le système retourne à la liste des tâches sans supprimer la tâche.               |

[Retour](/.UML/FunctionalSpecifications.md)
