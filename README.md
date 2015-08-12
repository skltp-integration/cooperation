## Synopsis

## Getting started

> **Förutsättning:**

> Följande programvaror måste finnas installerade för att kunna bygga och köra applikationen.
> - Java 8 eller högre
> - Maven 3.*
> - Git

### Hämta ut källkoden
```
> git clone git@github.com:skltp-incubator/cooperation.git
```

### Bygg applikationen
```
> mvn clean package
```

### Starta Applikationen
Med  Maven:
```
> mvn spring-boot:run
```

Som standalone:
```
> java -jar target/cooperation-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```
