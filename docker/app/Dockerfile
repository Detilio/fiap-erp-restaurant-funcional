FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /build
COPY . /build

RUN ./mvnw package -DskipTests

FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /build/target/*.jar /app/run.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/run.jar"]