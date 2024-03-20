# Spring Boot Security Starter - Basic Auth drop-in Module
*Read Me file authored by Henrik Augustsson, on 2022-05-19, at Nordic Medtest.*

##Installation

### Pom File
Import following dependencies into an appropriate pom.xml-file.
Note the version tag in the first dependency.

```xml
<dependencies>
    <!-- spring boot security basic auth module -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
        <version>2.3.8.RELEASE</version> <!-- select correct version! -->
    </dependency>

    <!-- spring boot security basic auth module -->
    <dependency>
        <groupId>org.springframework.security</groupId>
        <artifactId>spring-security-test</artifactId>
        <scope>test</scope>
    </dependency>

    <!-- spring boot security basic auth module -->
    <dependency> <!-- json serialization -->
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.9.0</version>
    </dependency>
</dependencies>

```
###Application Class
Add the scheduling property to the Spring Boot Application-class, if it doesn't already exist, like below: **@EnableScheduling**

This property is used to enable the automatic periodic file reads.

```java
@EnableScheduling
public class DemoApplication {
  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }
}
```

###Settings Class
Adjust values herein to point to where from you wish to read the user file. You can also adjust the wrapping path-uri to the auth-control endpoints. Tou can also allow or disallow certain endpoints used by the AuthController.

###BasicAuthConfig Class
Within the configure-function, set up which uri ant-patterns should correspond to which user roles, or no roles, as per the examples given.

The lines below will require USER group on all endpoints. <br> Some lines below might be redundant...

```java
class Example {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/").hasAuthority("USER")
        .antMatchers("/**").hasAuthority("USER")
        .anyRequest().authenticated()
        .and()
        .httpBasic();
  }
}
```

## Usage
### User File
On first start, a sample user file with 'dummy users' will be created, if one was not found at the configured location.
### Make adjustments to user file
Follow the examples given in the user file to create new users. Variables are provided for contact information, but is not used in the app.
### Versioning and Backup
This drop-in-module does not have any built in backup- or versioning method, since it is designed around the existence of a simple json file. It is recommended to use some external method, like Git, to safe-keep a copy of the user file. Use scripts to download the file from your versioning, backup, or file-store solution and to place it where it needs to be read from.
