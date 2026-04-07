| Elément                                                           | Description                                                                            |
| ----------------------------------------------------------------- | -------------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                                      | Associer une tâche à une catégorie                                                     |
| **But / Objectif**                                                | Permettre à un utilisateur authentifié d’associer une tâche existante à une catégorie. |
| **Acteur principal**                                              | Utilisateur authentifié                                                                |
| **Acteur secondaire**                                             | Base de données                                                                        |
| **Préconditions**                                                 | L’utilisateur est authentifié. L’utilisateur a au moins une tâche et une catégorie.    |
| **Scénario principal**                                            |                                                                                        |
| 1. L’utilisateur sélectionne une tâche dans sa liste.             | Le système affiche les détails de la tâche.                                            |
| 2. L’utilisateur choisit une catégorie dans une liste déroulante. | Le système valide l’association.                                                       |
| 3. L’utilisateur valide l’association.                            | Le système met à jour la tâche dans la base de données.                                |
| 4. Le système affiche un message de confirmation.                 | La tâche apparaît maintenant dans la catégorie choisie.                                |
| **Scénario secondaire 1**                                         |                                                                                        |
| 1. La catégorie est supprimée entre-temps.                        | Le système affiche un message d’erreur : "Cette catégorie n’existe plus."              |

[Retour](/.UML/FunctionalSpecifications.md)
