FROM maven:4.0.0-rc-4 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM bellsoft/liberica-openjre-debian:24-cds
COPY --from=build /home/app/target/*.jar /usr/local/lib/*.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/*.jar"]