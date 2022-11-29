### Översikt

> TAK REST-api (cooperation) deployas som en webapp på Tomcat.
> Uppslag görs mot en MySQL-databas.
> Det finns ett script som skall köras schedulerat en gång per dygn kl 02.30.

### Deploy av cooperation

> - Skapa MySQL-schema "cooperation" och ett konto som kan accessa det.

> - Kör ett groovy-script för initial create av tabeller.
  - Alla skript filer ligger under ../scripts/cooperation/
```
> groovy CreateNewTables.groovy  -url  url-till-cooperation-databasen  -u user -p password
```

> - Skapa en katalog där inputfilerna (tak-exporter från olika miljöer som skall komma via sftp/scp) skall ligga - input-file-dir.
  - Skapa en sftp-area där olika landsting kan leverera sina filer.
Notera förväntningar angående att hämta filer från ett dir i nästa punkt.
   - Konfigurera miljöberoende variabler i: cooperation-import-from-tak-env-setup.sh
Här finns just nu en förväntan om att alla TAK-filer kommer att kunna hämtas med SFTP från samma katalog.
Sätt upp SSH-nycklar, skapa nya med: ssh-keygen -t rsa -b 2048 -C "TAK data transfer"

> - För att få igång rullandet av tabeller från "_new"  till normal till "_old": Kör cooperation-import-from-tak.sh som exekverar tre groovy-script som skapar nya tabeller, laddar in filer och döper om tabellerna
(går bra med ett tomt directory eller med filer)
```
> cooperation-import-from-tak.sh skall även köras schedulerat varje natt kl 03.00.
```

> - På Tomcat-maskinen: skapa en katalog för en konfigurationfil för webappen
> Skapa en konfigfil som heter cooperation-config-override.properties i den katalogen i detta directory med innehåll (anpassat efter lokala miljön)
```
spring.datasource.url=jdbc:mysql://localhost/cooperation
spring.datasource.username=root
spring.datasource.password=
```
> - Konfigurera Tomcat-environment variabler i environment fil
```
CATALINA_OPTS="-Xms1024m -Xmx2048m -XX:NewSize=256m -XX:PermSize=128m -XX:MaxPermSize=512m -Dspring.profiles.active=production -Dapp.conf.dir=/var/spool/tomcat/cooperation/etc"
```
> där den sista variabeln ska peka på den konfig-katalog som skapades i steg 5 ovan.

> - Deploya WAR-filen i Tomcat

> symlink /www/ind/war/cooperation/coop.war ska peka på ny cooperation version

> starta om tomcat  

> - Verifiera deployment
> Öppna webläsare 
```
url-till-tomcat[:port]/coop/api/v1/connectionPoints
```
Skall ge en xml-utskrift

> - Klart
