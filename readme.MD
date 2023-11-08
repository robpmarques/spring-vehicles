## Running Vehicles Service locally
VehiclesService is an [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven/). You can build a jar file and run it from the command line.


```
git clone https://github.com/robpmarques/spring-vehicles
cd spring-vehicles
./mvnw package
java -jar target/*.jar
```

You can then access VehiclesService at http://localhost:8080/

```
./mvnw spring-boot:run
```

## Database configuration

You can use the provided `docker-compose.yml` file to start the database containers or any database configured in the OS.

```
$ docker-compose postgres up
```

### Steps:

1) On the command line run:
    ```
    git clone https://github.com/spring-projects/spring-petclinic.git
    ```

2) IDEA of Preference
    Open the project in the "vehicles" page so you can start modifying the code
    
3) Run the project
    You can use ./mvnw spring-boot:run to run the project

3) Navigate to /vehicles endpoint to get all vehicles
    Visit [http://localhost:8080/api/vehicles](http://localhost:8080/api/vehicles) in your browser.


Navigate to [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) to dive into the endpoints documentation.
