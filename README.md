# assembly-service

![project arch](https://i.imgur.com/jTsr8QQ.png)

## Running

1. Build the project
```
cd assembly-service
./gradlew clean build
```
2. Build docker compose
```
cd ..
docker-compose build
```
3. Run docker compose
```
docker-compose up
```

### Running project local

1. Run docker-compose-local
```
docker-compose -f docker-compose-local.yml up
```

2. Run project by your way
```
java -jar 
or
direct by your IDE
```

## Decisions

## Used technologies