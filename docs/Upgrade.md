### Uppgradering till cooperation 1.3.1 från valfri tidigare version

> - Uppdatera scripten CreateNewTables.groovy och TakCooperationImport.groovy och ActivateNewVersion.groovy
  - Alla skript filer ligger under ../scripts/cooperation/

> - Kör cooperation-import-from-tak.sh som exekverar tre groovy-script som skapar nya tabeller, laddar in filer och döper om tabellerna.
> Kontrollera att inga felutskrifter

> - Deploya den nya WAR-filen  1.3.1 i Tomcat

> - Starta om

> - Verifiera deployment

> Öppna webläsare 
```
url-till-tomcat[:port]/coop/api/v1/connectionPoints?api_key=abcd
```
> Skall ge en xml-utskrift

> - Klart
