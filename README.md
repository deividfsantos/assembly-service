# assembly-service
The purpose of this project is to provide APIs for an application that can execute the creation of an assembly, where it is possible for members to vote positively or negatively on the agenda.

### Application design
![project arch](https://i.imgur.com/jTsr8QQ.png)

### Running

0. To execute the project it is **necessary** to have **Docker** and **Docker-compose** installed.

1. You need to **build** the project to create the jar
```
cd assembly-service
./gradlew clean build
```
2. Back to root project folder to **build** the **docker compose**
```
cd ..
docker-compose build
```
3. Then **run docker compose**
```
docker-compose up
```
4. Access **localhost:8080** to see the app documentation and execution with swagger


#### Running project local
This option is to run the project with your favorite IDE or with java -jar. It just up the external dependencies of the project.

1. Run docker compose alternative file that **just run external dependencies** from the project
```
docker-compose -f docker-compose-local.yml up
```
2. Run project by your way
```
java -jar 
or direct by your IDE
```
3. Access **localhost:8080** to see the app documentation and execution with swagger

#### Running tests
```
./gradlew clean test 
```
#### Running mutation tests
```
./gradlew pitest 
```

### Decisions
The choice of technologies was made because they are already stable and tested in the market, thus obtaining more security, they are also technologies that have a large community, which can be useful in solving possible problems.

### Used technologies
- **Java 11**: Java is a consolidated language and is widely used in the market, for this reason the use of it. Together with Spring boot it is easy and simple to create API's rest.
- **JUnit 5**: Junit5 is used to do the tests, the latest version brings improvements and makes it easier to create unit tests.
- **Spring Boot 2.3.1**: Spring boot is the java framework that greatly facilitates the configuration and creation of API's rest.
- **Gradle 6.4.1**: Gradle is used to facilitate the project build and the organization and use of dependencies in the project, without needing to use XML files.
- **Swagger 2.9.2**: Swagger is a self-documentation tool for API's, that is, just use some annotations and Swagger itself will set up a documentation page and it is still possible to run the API's.
- **RabbitMQ 5.9.0**: Using RabbitMQ to send messages to queues.
- **Mysql 8**: MySQL is one of the most used and most stable databases on the market.
- **Docker**: The use of docker in the project is made to facilitate the execution of the complete environment and to have the service running together with its external dependencies.