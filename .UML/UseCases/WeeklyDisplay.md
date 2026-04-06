| Elément                                                                                  | Description                                                                           |
| ---------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                                                             | Filtrer les tâches par semaine                                                        |
| **But / Objectif**                                                                       | Permettre à un utilisateur authentifié de visualiser ses tâches groupées par semaine. |
| **Acteur principal**                                                                     | Utilisateur authentifié                                                               |
| **Acteur secondaire**                                                                    | Base de données                                                                       |
| **Préconditions**                                                                        | L’utilisateur est authentifié. Au moins une tâche avec une date existe.               |
| **Scénario principal**                                                                   |                                                                                       |
| 1. L’utilisateur sélectionne l’option "Afficher par semaine".                            | Le système regroupe les tâches par semaine en fonction de leur date.                  |
| 2. L’utilisateur consulte les tâches de la semaine en cours ou d’une semaine spécifique. | Le système affiche les tâches groupées.                                               |
| **Scénario secondaire 1**                                                                |                                                                                       |
| 1. Aucune tâche n’a de date définie.                                                     | Le système affiche un message : "Aucune tâche avec date définie pour cette semaine."  |

[Retour](/.UML/FunctionalSpecifications.md)
