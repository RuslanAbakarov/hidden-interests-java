FROM adoptopenjdk/openjdk13:ubi
RUN mkdir /opt/app
COPY hidden-interests-dl-0.0.1-jar-with-dependencies.jar /opt/app
CMD ["java", "-jar", "/opt/app/hidden-interests-dl-0.0.1-jar-with-dependencies.jar" "CHERKASY_PLENARY_SESSION"]