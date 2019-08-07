FROM openjdk:8-jre-alpine

ENV SPRING_PROFILES_ACTIVE ${SPRING_PROFILES_ACTIVE:-default}
ENV JAVA_OPTS ${JAVA_OPTS:-'-Xmx512m'}
ENV DEBUG_OPTS ${DEBUG_OPTS}
ENV PORT ${PORT:-8080}

RUN adduser -D -g '' java

ADD target/*.jar /app.jar
COPY src/main/resources/biblioteca-nacional-connector-2.0.0.jar /home
COPY src/main/resources/generico-provedor-1.0.0.jar /home


VOLUME /home

RUN sh -c 'touch /app.jar'

USER java

EXPOSE ${PORT}

CMD java ${JAVA_OPTS} ${DEBUG_OPTS} -Djava.security.egd=file:/dev/./urandom -jar /app.jar