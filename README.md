# Information about incidents

The main idea to this project is to show the most significant incidents on any point of the world.

The system is available to all the people that are interested in seeing the incidents. Each of these is identified with different symbols.

## Requisites

- Java >= 8
- Kafka (or)
- docker-compose

## Run

1. If you haven't Kafka installed you can run docker-compose available on the Kafka-docker directory to deploy the Kafka

    1.1 On kafka-docker directory run `docker-compose up`

2. Start transit spring-boot project

3. Open brower [here](localhost:8080)

4. Start the cp virtual sensor
    
    4.1 Inside cp folder run
    ```bash
    mvn clean compile exec:java
    ```

## Development information

#### Create an account to get a Bing Api Key

1. https://www.bingmapsportal.com/

2. Create an account

3. Go to `My account` and then click on `My keys`
 
4. Fill the fields with app data 

5. Get the key

#### Use key

1. Create `config.cfg` on  `src/main/java/ua.es.transit.resources`

2. In this file write just `key = XXXX` (XXXX is the key)

#### Databse H2 - view data present in the database

1. Access "http://localhost:8080/h2-console/"

2. JDBC URL: "jdbc:h2:/"+ (Where the folder transit is located) +"/transit/db/myDB" -> path to "myDB"

3. User Name: sa / Password: password

4. Connect

#### Some Notes - gitignore

1. Files named Test.java are ignore. 

## Authors

* **Catarina Silva** - [catarinaacsilva](https://github.com/catarinaacsilva)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

