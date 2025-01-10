# Cooperation (TAK-API)

Ett API för Tjänsteadresseringskatalogen (TAK) - [Mer information om TAK-API](https://inera.atlassian.net/wiki/x/AYA_y)

Cooperation-applikationen som innehåller API:et är byggd med Java (17) och Spring Boot.

Repot innehåller även script (bash/groovy) som körs för att uppdatera databasen med ny TAK-data,
samt en Helm chart-paketering för Kubernetes.

## Bygg applikationen
```
> mvn clean package
```
## Starta Applikationen lokalt
För utveckling används en dev-profil som använder en in-memory H2-databas.

Med  Maven:
```
> mvn spring-boot:run
```
Som standalone:
```
> java -jar target/cooperation-x.y.z-SNAPSHOT.jar --spring.profiles.active=dev
```
Med applikationen igång lokalt finns API-dokumentation (swagger) på denna URL:
```
http://localhost:8080/coop/doc/index_v2.html
```
