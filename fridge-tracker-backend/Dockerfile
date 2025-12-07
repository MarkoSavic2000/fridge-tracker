FROM maven:3.9-eclipse-temurin-25 AS deps
WORKDIR /app

COPY pom.xml .

COPY fridge-tracker-core/pom.xml fridge-tracker-core/
COPY fridge-tracker-core/fridge-tracker-application/pom.xml fridge-tracker-core/fridge-tracker-application/
COPY fridge-tracker-core/fridge-tracker-domain/pom.xml fridge-tracker-core/fridge-tracker-domain/

COPY fridge-tracker-adapters/pom.xml fridge-tracker-adapters/
COPY fridge-tracker-adapters/fridge-tracker-rest/pom.xml fridge-tracker-adapters/fridge-tracker-rest/

COPY fridge-tracker-infrastructure/pom.xml fridge-tracker-infrastructure/
COPY fridge-tracker-infrastructure/fridge-tracker-external-interfaces/pom.xml fridge-tracker-infrastructure/fridge-tracker-external-interfaces/
COPY fridge-tracker-infrastructure/fridge-tracker-persistence/pom.xml fridge-tracker-infrastructure/fridge-tracker-persistence/

COPY fridge-tracker-boot/pom.xml fridge-tracker-boot/

RUN mvn -q -B -DskipTests -DskipITs -Dmaven.main.skip=true -Dmaven.test.skip=true \
    dependency:go-offline || true



FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /app

COPY --from=deps /root/.m2 /root/.m2

COPY . .

RUN mvn -q -B -DskipTests -pl fridge-tracker-boot -am clean package



FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app

COPY --from=build /app/fridge-tracker-boot/target/fridge-tracker-boot-0.0.1.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
