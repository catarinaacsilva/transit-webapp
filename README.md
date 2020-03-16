# Information about incidents

The main idea to this project is to show the most significant incidents on any point of the world.

The system is available to all the people that are interested in seeing the incidents. Each of these is identified with different symbols.

## Requisites

- Java >= 8
- Kafka

## Run

1. If you aren't the Kafka installed you can run docker-compose available on my github to instantiate the Kafka.

    1.1 Open terminal and run `git clone https://github.com/catarinaacsilva/kafka-docker.git`
    1.2 Run `docker-compose up`
        

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


#### Some Notes - gitignore

1. Files named Test.java are ignore. 

## Authors

* **Catarina Silva** - [catarinaacsilva](https://github.com/catarinaacsilva)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

