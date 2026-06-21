FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre
RUN mkdir /app
COPY --from=build /app/target/*.jar /app/*.jar
COPY --from=build /app/.env /app
WORKDIR /app
CMD ["java", "-jar", "*.jar"]
