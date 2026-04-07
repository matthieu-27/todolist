| Elément                                               | Description                                                                      |
| ----------------------------------------------------- | -------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                          | Modifier une tâche                                                               |
| **But / Objectif**                                    | Permettre à un utilisateur authentifié de mettre à jour une tâche existante.     |
| **Acteur principal**                                  | Utilisateur authentifié                                                          |
| **Acteur secondaire**                                 | Base de données                                                                  |
| **Préconditions**                                     | L’utilisateur est authentifié. L’utilisateur a au moins une tâche dans sa liste. |
| **Scénario principal**                                |                                                                                  |
| 1. L’utilisateur sélectionne une tâche dans sa liste. | Le système affiche les détails de la tâche dans un formulaire pré-rempli.        |
| 2. L’utilisateur modifie les champs souhaités.        | Le système valide les modifications.                                             |
| 3. L’utilisateur valide les changements.              | Le système met à jour la tâche dans la base de données.                          |
| 4. Le système affiche un message de confirmation.     | La liste des tâches reflète les modifications.                                   |
| **Scénario secondaire 1**                             |                                                                                  |
| 1. La tâche a été supprimée entre-temps.              | Le système affiche un message d’erreur : "Cette tâche n’existe plus."            |
| **Scénario secondaire 2**                             |                                                                                  |
| 1. L’utilisateur supprime un champ obligatoire.       | Le système affiche un message d’erreur : "Le champ [X] ne peut pas être vide."   |

[Retour](/.UML/FunctionalSpecifications.md)
