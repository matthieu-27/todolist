| Elément                                            | Description                                                                                        |
| -------------------------------------------------- | -------------------------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                       | Consulter les tâches fictives                                                                      |
| **But / Objectif**                                 | Permettre à un utilisateur non authentifié de visualiser des exemples de tâches en page d’accueil. |
| **Acteur principal**                               | Utilisateur non authentifié                                                                        |
| **Acteur secondaire**                              | Base de données                                                                                    |
| **Préconditions**                                  | L’utilisateur n’est pas authentifié. L’application est en ligne et accessible.                     |
| **Scénario principal**                             |                                                                                                    |
| 1. L’utilisateur accède à la page d’accueil.       | Le système affiche une liste de tâches fictives (exemples).                                        |
| 2. L’utilisateur consulte les détails d’une tâche. | Le système affiche les détails (nom, description, date).                                           |
| **Scénario secondaire 1**                          |                                                                                                    |
| 1. L’utilisateur n’a pas accès à Internet.         | Le système affiche un message d’erreur : "Pas de connexion Internet".                              |
| **Scénario secondaire 2**                          |                                                                                                    |
| 1. Aucune tâche fictive n’est disponible.          | Le système affiche un message : "Aucune tâche fictive disponible pour l’instant."                  |

[Retour](/.UML/FunctionalSpecifications.md)
