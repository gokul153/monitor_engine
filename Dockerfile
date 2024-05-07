FROM docker-prod.abreg.arabbank.com.jo/base/openjdk:17-jdk-slim AS build-env
COPY target/*.jar /app/main.jar
RUN chown -R nobody /app/main.jar && chmod 777 /app/main.jar

FROM docker-prod.abreg.arabbank.com.jo/base/openjdk:17-jdk-slim
#FROM  vishal3152/openjdk:11-jre-slim-bullseye
COPY --from=build-env /app /app
COPY --from=build-env /etc/passwd /etc/shadow /etc/
RUN addgroup nobody
USER nobody:nobody
USER nobody:nobody
WORKDIR /app
ENV _JAVA_OPTIONS "-XX:MaxRAMPercentage=90 -Djava.security.egd=file:/dev/./urandom"
CMD ["java", "-jar", "main.jar"]