FROM groovy:3.0.13-jdk8

COPY --chown=groovy:groovy cooperation/*.groovy scripts/
COPY --chown=groovy:groovy cooperation/*.sh scripts/

RUN groovy scripts/grabit.groovy
