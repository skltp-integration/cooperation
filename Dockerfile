FROM tomcat:9-jdk8

ENV APP_NAME=coop \
    WARFILE=target/cooperation-1.5.1-SNAPSHOT.war

ADD $WARFILE $CATALINA_HOME/webapps/$APP_NAME.war
