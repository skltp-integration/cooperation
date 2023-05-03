FROM tomcat:9-jdk11

ENV APP_NAME=coop \
    WARFILE=target/cooperation-*.war

ADD $WARFILE $CATALINA_HOME/webapps/$APP_NAME.war
