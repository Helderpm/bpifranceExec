# bpifranceExec
Consignes pour l'exercice technique
Pour l'exercice on va se concentrer sur une définition restreinte.
On considèrera comme bénéficiaire effectif d'une entreprise une personne physique qui en détient
directement ou indirectement plus de 25% du capital.
Il s'agit des cas 1, 2 et 3 représentés dans le document suivant :
https://www.greffe-tc/paris.fr/uploads/paris/Fiches%20RCS/RBE_Fiche_pratique_schemas.pdf

Mise en situation
Le métier veut remplacer le système existant par un outil interne permettant de gérer les bénéficiaires
effectifs d'une entreprise.
Tu es développeur dans l'équipe qui a été choisie pour remplir cette mission. Pour commencer l'équipe doit
livrer une API REST qui permette :
de récupérer pour une entreprise donnée la liste des bénéficiaires effectifs
de rajouter une nouvelle entreprise dans le système
de rajouter une nouvelle personne physique dans le système
de rajouter pour une entreprise un nouveau bénéficiaire (soit une entreprise / soit une personne physique)

--------------------------------//--------------------------------//--------------------------------

## Architecture

L'application suit une architecture hexagonale (Ports & Adapters) pour séparer les concerns métier de l'infrastructure :

### Structure des packages
- **domaine** : Contient la logique métier pure et les ports (interfaces)
  - `port/in` : Ports d'entrée (use cases)
  - `port/out` : Ports de sortie (repositories)
- **application** : Services d'application implémentant les ports d'entrée
- **infrastructure** : Adaptateurs techniques
  - `sql` : Persistance avec JPA/Hibernate
  - `rest` : Clients HTTP externes si nécessaire
- **exposition** : Couche de présentation
  - `rest` : Contrôleurs REST et DTOs
- **configuration** : Configuration Spring et beans

### Principes appliqués
- Dependency Inversion : Le domaine ne dépend que d'interfaces
- Single Responsibility : Chaque classe a une responsabilité unique
- Clean Architecture : Séparation stricte entre métier et technique

## Stack Technique

### Backend
- **Java 25** : Dernière version LTS de Java
- **Spring Boot 3.4.5** : Framework principal
- **Spring Data JPA** : Persistance des données
- **Spring Validation** : Validation des entrées
- **H2 Database** : Base de données en mémoire (v2.3.232)

### Qualité & Tests
- **JUnit 5** : Tests unitaires
- **Cucumber 7.15.0** : Tests d'intégration BDD
- **AssertJ** : Assertions fluides
- **Mockito** : Mocking pour tests

### Outils de développement
- **Lombok** : Réduction du code boilerplate
- **MapStruct 1.6.3** : Mapping entre objets
- **SpringDoc OpenAPI 2.8.8** : Documentation API automatique
- **Spring Boot DevTools** : Rechargement automatique

### Build & dépendances
- **Maven** : Gestion des dépendances et build
- **Java 25** : Compilation et exécution

## Lancement de l'application

### Prérequis
- Java 25 ou supérieur
- Maven 3.8+ installé

### Compilation
```bash
mvn clean install
```

### Démarrage
```bash
mvn spring-boot:run
```

L'application démarre sur `http://localhost:8080`

### Tests
```bash
# Tests unitaires
mvn test

# Tests d'intégration
mvn verify

# Tous les tests
mvn clean verify
```

### Documentation API
Une fois l'application démarrée, accédez à :
- **Swagger UI** : `http://localhost:8080/swagger-ui/index.html`
- **OpenAPI JSON** : `http://localhost:8080/v3/api-docs`

## Déploiement

### Build de production
```bash
mvn clean package -DskipTests
```

### Exécution du JAR
```bash
java -jar target/exec-0.0.1-SNAPSHOT.jar
```

### Configuration environnement
Les propriétés peuvent être configurées via :
- `application.properties` (par défaut)
- Variables d'environnement
- Arguments de ligne de commande

### Base de données
Par défaut, l'application utilise H2 en mémoire avec la console activée.

**Configuration actuelle :**
```properties
spring.datasource.url=jdbc:h2:mem:beneficiaire_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

**Console H2 :** `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:beneficiaire_db`
- Username: `sa`
- Password: `password`

Pour une base persistante (PostgreSQL) :
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bpifranceexec
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
```

### Docker (optionnel)
```dockerfile
FROM openjdk:25-jdk-slim
COPY target/exec-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## API Endpoints

L'API expose les endpoints suivants pour la gestion des bénéficiaires effectifs :

### Entreprises
- `GET /api/entreprises` - Lister toutes les entreprises
- `POST /api/entreprises` - Créer une nouvelle entreprise
- `GET /api/entreprises/{id}` - Récupérer une entreprise par son ID
- `GET /api/entreprises/{id}/beneficiaires` - Lister les bénéficiaires effectifs d'une entreprise

### Personnes Physiques
- `GET /api/personnes` - Lister toutes les personnes physiques
- `POST /api/personnes` - Créer une nouvelle personne physique
- `GET /api/personnes/{id}` - Récupérer une personne physique par son ID

### Bénéficiaires Effectifs
- `POST /api/entreprises/{entrepriseId}/beneficiaires` - Ajouter un bénéficiaire effectif à une entreprise

**Note :** Un bénéficiaire effectif est une personne physique ou morale détenant plus de 25% du capital d'une entreprise.

## Réalisation du Projet

### Fonctionnalités implémentées ✅
- **Architecture hexagonale** propre avec séparation des domaines
- **API REST complète** pour la gestion des bénéficiaires effectifs
- **Persistance JPA/Hibernate** avec H2 database
- **Tests unitaires** avec JUnit 5 et Mockito
- **Tests d'intégration** BDD avec Cucumber
- **Documentation API** automatique avec SpringDoc OpenAPI
- **Documentation JavaDoc complète** pour tous les packages métier et application
- **Gestion des erreurs** avec exceptions fonctionnelles et techniques spécifiques
- **Tests unitaires complets** avec couverture de toutes les exceptions (29 tests passants)
- **Validation des données** avec Spring Validation
- **Mapping d'objets** avec MapStruct
- **Console H2** pour l'exploration des données

## Documentation du Code

### Couverture JavaDoc 📚
Le projet bénéficie d'une documentation JavaDoc complète et professionnelle :

#### Package `domaine.model`
- **Beneficiaire** : Record avec méthodes de validation métier
- **Entreprise** : Record avec validations SIRET et complétude
- **PersonnePhysique** : Record avec utilitaires de formatage et validation

#### Package `domaine.port`
- **GestionBeneficiairePort** : Interface des cas d'utilisation avec documentation détaillée
- **BeneficiaireRepositoryPort** : Interface de persistance avec contrats clairs

#### Package `application`
- **GestionBeneficiaireService** : Service d'application avec documentation complète des opérations

### Standards de Documentation
- **Français** : Toute la documentation est en français pour cohérence métier
- **JavaDoc complète** : Classes, méthodes, paramètres, retours et exceptions
- **Références croisées** : Tags @see pour lier les classes/interfaces
- **Exemples d'utilisation** : Documentation des filtres et cas d'usage
- **Architecture expliquée** : Rôle de chaque composant dans l'architecture hexagonale

### Améliorations possibles 🔄
- **Responses standardisées** : Implémenter une classe de réponse uniforme pour l'API
- **Base de données persistante** : Configurer PostgreSQL ou MySQL pour la production
- **Sécurité** : Ajouter Spring Security avec JWT/OAuth2
- **Monitoring** : Intégrer Actuator et des métriques
- **Tests complémentaires** : Ajouter des tests de charge et de sécurité

### Contraintes rencontrées ⚠️
- **Temps limité** : 2 heures pour implémenter l'ensemble des fonctionnalités
- **Polyvalence technique** : Implémentation dans d'autres langages à explorer
- **Concepts DevOps** : Pipeline CI/CD et conteneurisation à approfondir
