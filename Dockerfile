FROM maven AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM adoptopenjdk/openjdk13:ubi
COPY --from=build /home/app/target/hidden-interests-dl-0.0.1-jar-with-dependencies.jar /usr/local/lib/go.jar
ENTRYPOINT ["java","-jar","/usr/local/lib/go.jar", "CHERKASY_PLENARY_SESSION"]
