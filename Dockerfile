FROM maven:latest AS build
RUN mkdir /app
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:latest
RUN mkdir /app
COPY --from=build /app/target/*.jar /app/*.jar
copy --from=build /app/.env /app
WORKDIR /app
CMD "java" "-jar" "*.jar"