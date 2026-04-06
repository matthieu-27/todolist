| Elément                                                                    | Description                                                                                                   |
| -------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------- |
| **Nom du cas d'utilisation**                                               | Créer une tâche                                                                                               |
| **But / Objectif**                                                         | Permettre à un utilisateur authentifié d’ajouter une nouvelle tâche avec nom, date, description et catégorie. |
| **Acteur principal**                                                       | Utilisateur authentifié                                                                                       |
| **Acteur secondaire**                                                      | Base de données                                                                                               |
| **Préconditions**                                                          | L’utilisateur est authentifié. Le formulaire d’ajout de tâche est accessible.                                 |
| **Scénario principal**                                                     |                                                                                                               |
| 1. L’utilisateur clique sur "Ajouter une tâche".                           | Le système affiche un formulaire vide (nom, date, description, catégorie).                                    |
| 2. L’utilisateur remplit les champs obligatoires (nom, date, description). | Le système valide les champs.                                                                                 |
| 3. L’utilisateur valide la création.                                       | Le système enregistre la tâche dans la base de données.                                                       |
| 4. Le système affiche un message de confirmation.                          | La tâche apparaît dans la liste des tâches de l’utilisateur.                                                  |
| **Scénario secondaire 1**                                                  |                                                                                                               |
| 1. L’utilisateur ne remplit pas un champ obligatoire.                      | Le système affiche un message d’erreur : "Veuillez remplir tous les champs obligatoires."                     |
| **Scénario secondaire 2**                                                  |                                                                                                               |
| 1. La date saisie est antérieure à aujourd’hui.                            | Le système affiche un avertissement : "La date est antérieure à aujourd’hui. Voulez-vous continuer ?"         |
| **Scénario secondaire 3**                                                  |                                                                                                               |
| 1. L’utilisateur choisit une catégorie non existante.                      | Le système propose de créer la catégorie ou d’en choisir une autre.                                           |

[Retour](/.UML/FunctionalSpecifications.md)
