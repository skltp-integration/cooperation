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
se.skltp.cooperation.accesscontrol=true	
se.skltp.cooperation.api_key=abcd
```
> api_key kan sättas till valfri sträng


> - Konfigurera Tomcat-environment variabler
> Lägg till dessa i TOMCAT_HOME/bin/setenv.sh eller motsvarande:
```
export CATALINA_OPTS="$CATALINA_OPTS -Dspring.profiles.active=prod"
export CATALINA_OPTS="$CATALINA_OPTS -Dapp.conf.dir=/opt/tomcat/conf/"
```
> där den sista variabeln ska peka på den konfig-katalog som skapades i steg 5 ovan.

> - Deploya WAR-filen i Tomcat
/www/inera/releases/cooperation-1.1.0.war
> Den externt exponerade contextroot skall vara coop för att den interaktiva api-dokumentation mha swagger skall fungera, tex genom att döpa om filen till coop.war innan deployment.

> - Verifiera deployment
> Öppna webläsare 
```
url-till-tomcat[:port]/coop/api/v1/connectionPoints?api_key=abcd
```
Skall ge en xml-utskrift

> - Klart
