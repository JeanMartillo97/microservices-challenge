FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml /app/pom.xml
COPY customer-service/pom.xml /app/customer-service/pom.xml
COPY account-service/pom.xml /app/account-service/pom.xml

RUN mvn -q -e -DskipTests dependency:go-offline

COPY customer-service /app/customer-service
COPY account-service /app/account-service

RUN mvn -q -pl customer-service -am -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/customer-service/target/*.jar /app/app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
