# M-Motors — Back-end

API REST Spring Boot pour l'application M-Motors, spécialiste en vente et location de véhicules d'occasion.

**API en ligne :** https://mmotors-back-production.up.railway.app

---

## Technologies

- Java 21
- Spring Boot 4.0.6
- Spring Security + JWT
- Spring Data JPA + Hibernate
- PostgreSQL 16
- Spring Actuator
- Logback
- JUnit 5 + Mockito + JaCoCo

---

## Structure du projet

```
mmotors/
├── src/main/java/com/mmotors/mmotors/
│   ├── AuthController.java         # Inscription, connexion, réinitialisation mdp
│   ├── VehiculeController.java     # CRUD véhicules (public en lecture, admin en écriture)
│   ├── DossierController.java      # Dépôt et consultation des dossiers (client)
│   ├── AdminController.java        # Gestion dossiers et véhicules (admin)
│   ├── ProfilController.java       # Consultation et modification du profil client
│   ├── Client.java                 # Entité client
│   ├── Dossier.java                # Entité dossier
│   ├── Vehicule.java               # Entité véhicule
│   ├── ClientRepository.java       # Requêtes JPA client
│   ├── VehiculeRepository.java     # Requêtes JPA véhicule
│   ├── DossierRepository.java      # Requêtes JPA dossier
│   ├── JwtService.java             # Génération et validation des tokens JWT
│   ├── JwtFilter.java              # Filtre Spring Security (intercepte chaque requête)
│   ├── SecurityConfig.java         # Configuration Spring Security + CORS
│   └── GlobalExceptionHandler.java # Gestion centralisée des erreurs
├── src/main/resources/
│   ├── application.properties      # Configuration (BDD, JPA, JWT)
│   └── logback-spring.xml          # Configuration des logs
└── src/test/                       # Tests JUnit 5 + Mockito (88% JaCoCo)
```

---

## Installation locale

### Prérequis

- Java 21
- Maven
- PostgreSQL 16
- IntelliJ IDEA (recommandé)

### Étapes

1. Cloner le dépôt :
```bash
git clone https://github.com/Yann0411/mmotors-back.git
cd mmotors-back
```

2. Créer la base de données PostgreSQL :
```sql
CREATE DATABASE mmotors;
CREATE USER mmotors WITH PASSWORD 'mmotors';
GRANT ALL PRIVILEGES ON DATABASE mmotors TO mmotors;
```

3. Copier le fichier de configuration :
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

4. Renseigner les valeurs dans `application.properties` :
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mmotors
spring.datasource.username=mmotors
spring.datasource.password=mmotors
```

5. Lancer l'application :
```bash
mvn spring-boot:run
```

> Hibernate crée automatiquement les tables au premier démarrage (`ddl-auto=update`).

L'API est disponible sur `http://localhost:8080`.

---

## Endpoints principaux

| Méthode | URL | Accès | Description |
|---------|-----|-------|-------------|
| POST | `/auth/inscription` | Public | Créer un compte |
| POST | `/auth/connexion` | Public | Se connecter |
| POST | `/auth/reinitialiser-mot-de-passe` | Public | Réinitialiser le mot de passe |
| GET | `/vehicules` | Public | Lister les véhicules |
| POST | `/dossiers` | CLIENT | Déposer un dossier |
| GET | `/dossiers/moi` | CLIENT | Mes dossiers |
| PUT | `/dossiers/{id}` | CLIENT | Modifier un dossier |
| DELETE | `/dossiers/{id}` | CLIENT | Annuler un dossier |
| GET | `/profil` | CLIENT | Mon profil |
| PUT | `/profil` | CLIENT | Modifier mon profil |
| GET | `/admin/dossiers` | ADMIN | Tous les dossiers |
| PUT | `/admin/dossiers/{id}/statut` | ADMIN | Valider / refuser |
| POST | `/admin/vehicules` | ADMIN | Ajouter un véhicule |
| PUT | `/admin/vehicules/{id}` | ADMIN | Modifier un véhicule |
| DELETE | `/admin/vehicules/{id}` | ADMIN | Supprimer un véhicule |
| GET | `/actuator/health` | Public | État de l'application |

---

## Tests

```bash
mvn test
```

Rapport JaCoCo généré dans `target/site/jacoco/index.html` — couverture actuelle : **88%**.

---

## Déploiement (Railway)

Le back-end est déployé automatiquement sur Railway à chaque `git push` sur `main`.

Variables d'environnement à configurer sur Railway :
```
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
JWT_SECRET_KEY
```
