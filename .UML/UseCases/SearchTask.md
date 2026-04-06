| Elément                                                    | Description                                                                                |
| ---------------------------------------------------------- | ------------------------------------------------------------------------------------------ |
| **Nom du cas d'utilisation**                               | Rechercher une tâche                                                                       |
| **But / Objectif**                                         | Permettre à un utilisateur authentifié de rechercher une tâche par nom, date ou catégorie. |
| **Acteur principal**                                       | Utilisateur authentifié                                                                    |
| **Acteur secondaire**                                      | Base de données                                                                            |
| **Préconditions**                                          | L’utilisateur est authentifié. La barre de recherche est accessible.                       |
| **Scénario principal**                                     |                                                                                            |
| 1. L’utilisateur utilise la barre de recherche.            | Le système affiche un champ de saisie.                                                     |
| 2. L’utilisateur saisit un critère (nom, date, catégorie). | Le système recherche les tâches correspondantes.                                           |
| 3. Le système affiche les résultats.                       | L’utilisateur peut consulter les détails de chaque tâche.                                  |
| **Scénario secondaire 1**                                  |                                                                                            |
| 1. Aucun résultat n’est trouvé.                            | Le système affiche un message : "Aucune tâche trouvée pour ce critère."                    |

[Retour](/.UML/FunctionalSpecifications.md)
