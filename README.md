# bpifranceExec
Consignes pour lʼexercice technique
Pour lʼexercice on va se concentrer sur une définition restreinte.
On considèrera comme bénéficiaire effectif dʼune entreprise une personne physique qui en détient
directement ou indirectement plus de 25% du capital.
Il sʼagit des cas 1, 2 et 3 représentés dans le document suivant :
https://www.greffe-tc/paris.fr/uploads/paris/Fiches%20RCS/RBE_Fiche_pratique_schemas.pdf

Mise en situation
Le métier veut remplacer le système existant par un outil interne permettant de gérer les bénéficiaires
effectifs dʼune entreprise.
Tu es développeur dans lʼéquipe qui a été choisie pour remplir cette mission. Pour commencer lʼéquipe doit
livrer une API REST qui permette :
de récupérer pour une entreprise donnée la liste des bénéficiaires effectifs
de rajouter une nouvelle entreprise dans le système
de rajouter une nouvelle personne physique dans le système
de rajouter pour une entreprise un nouveau bénéficiaire (soit une entreprise / soit une personne physique)

--------------------------------//--------------------------------//--------------------------------

* Comment fonctionne l’API (comment la lancer, comment appeler les routes, toute autre information
  utile, …)
  => l'application doit être compilée avec la commande 'mvn clean install', aller sur l'url de Swagger pour tester ( http://localhost:8080/swagger-ui/index.html )
*
* Ce que tu as réussi à faire
  => Mise en place d'un socle technique en respectant les bonnes pratiques et des API's rest pour le verifier.
  =>Ajout de test integration qui respecte le BDD.
*
* Ce que tu aurais aimé améliorer dans ton code
  => Ajouter d'autre API's
  => Class du domaine pour gerer les expection fonctionelle et technique
  => Class de l' exposition pour gerer les responses
  => Ajout de documentation pour mieux compreendre les methods
  => mise en place d' un contenaire de base de donnée: sql ou noSql ( à reflaichir )
*
* Ce que tu n’aurais pas réussi à faire (très utile pour l’entretien suivant dans notre process 🙂)
  => faire tout cela dans le temps de 2h en respectant les bonnes pratiques ( conception, unit test , test integration , ... )
  => le faire dans un autre language
  => Concepts devOps / Ops peuveut devinir un frein dans ma comprehension