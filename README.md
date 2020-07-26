# assembly-service
The purpose of this project is to provide APIs for an application that can execute the creation of an assembly, where it is possible for members to vote positively or negatively on the agenda.

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

0. To execute the project it is **necessary** to have **Docker** and **Docker-compose** installed.

1. Run docker compose alternative file that **just run dependencies** from the project
```
docker-compose -f docker-compose-local.yml up
```
2. Run project by your way
```
java -jar 
or
direct by your IDE
```
3. Access **localhost:8080** to see the app documentation and execution with swagger

### Decisions

### Used technologies
- **Java 11**: Desc
- **JUnit 5**: Desc
- **Spring Boot 2.3.1**: Desc
- **Gradle 6.4.1**: Desc
- **Swagger 2.9.2**: Desc
- **RabbitMQ 5.9.0**: Desc
- **Mysql 8**: Desc
- **Docker**: Desc