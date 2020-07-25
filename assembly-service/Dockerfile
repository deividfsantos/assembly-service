FROM openjdk:11
ARG JAR_FILE
COPY './build/libs/assembly-0.0.1-SNAPSHOT.jar' '/assembly-0.0.1-SNAPSHOT.jar'
COPY './wait-for-it.sh' '/wait-for-it.sh'
RUN chmod +x /wait-for-it.sh
ENTRYPOINT ["./wait-for-it.sh","assembly-db:3306","--","java","-jar","/assembly-0.0.1-SNAPSHOT.jar"]