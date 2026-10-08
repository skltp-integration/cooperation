FROM groovy:4.0-jdk21

USER root
RUN apt-get update \
    && apt-get install -y --no-install-recommends openssh-client \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /opt/cooperation

COPY --chown=root:root cooperation/*.groovy scripts/
COPY --chown=root:root cooperation/*.sh scripts/
COPY --chown=root:root logback-ecs.xml scripts/logback.xml

RUN chmod 755 scripts \
 && chmod 644 scripts/*.groovy scripts/logback.xml \
 && chmod 755 scripts/*.sh

USER groovy
RUN groovy scripts/grabit.groovy
