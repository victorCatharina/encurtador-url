FROM maven:3.9-amazoncorretto-21 AS build

COPY ../src /app/src
COPY ../pom.xml /app

WORKDIR /app
RUN mvn clean package -DskipTests

FROM amazoncorretto:21-alpine AS runtime

COPY --from=build /app/target/encurtador-url-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]