package pt.ua.deti;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main execution program.
 * 
 * @author Catarina Silva
 * @version 1.0
 */
public class Main {
    public static final String LINES[] = { "sintra", "minho", "braga", "oeste", "beiraAlta", "cascais", "tomar",
            "norte", "douro", "guimaraes", "beiraBaixa", "cintura", "algarve", "casaBranca", "sado", "aveiro",
            "fertagus" };
    public static final Map<String, String> latest = new LinkedHashMap<>();

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(final String[] args) {
        // create topics into kafka
        createTopics();

        // create a kafka producer
        final Properties properties = loadProperties("kafka.properties");
        final Producer<String, String> producer = new KafkaProducer<String, String>(properties);

        // setup the scheduler
        final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> publishData(producer), 0, 30, TimeUnit.SECONDS);

        // Lambda used to catch ctrl+c (interrupt signal)
        // and stop the scheduled executor
        Runtime.getRuntime().addShutdownHook(new Thread(() -> executorService.shutdown()));

        try {
            // wait for the scheduled executor to terminate
            while (!executorService.awaitTermination(24L, TimeUnit.HOURS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // close the kafka producer
            producer.close();
        }
    }

    /**
     * Consume data from suprimidos.pt and publish it into kafka
     * @see <a href="https://suprimidos.pt/atrasos">suprimidos</a>
     */
    public static void publishData(final Producer<String, String> producer) {
        logger.info("publishData");
        final ObjectMapper mapper = new ObjectMapper();
        try {
            for (final String line : LINES) {
                final Map<String, Object> j = HTTP.getJson("https://api.suprimidos.pt/data.php?lineDelay=" + line);

                final String id = Utils.cast(j.get("id"));

                if (!id.equals(latest.get(line))) {
                    logger.info("Publish new data from "+line);
                    latest.put(line, id);
                    final String value = mapper.writeValueAsString(j);
                    final ProducerRecord<String, String> record = new ProducerRecord<>("delay", value);
                    producer.send(record);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the topic list and insert it into kafka.
     * <p>
     * It creates a single topic for the delays.
     */
    public static void createTopics() {
        final Properties properties = loadProperties("kafka.properties");
        final AdminClient adminClient = AdminClient.create(properties);
        final List<NewTopic> newTopics = new ArrayList<NewTopic>();
        final NewTopic newTopic = new NewTopic("delay", 1, (short) 1);
        newTopics.add(newTopic);
        adminClient.createTopics(newTopics);
        adminClient.close();
    }

    /**
     * Load the properties file with the kafka configuration
     * @param file the path to the configuration
     * @return {@link Properties} class
     */
    public static Properties loadProperties(final String file) {
        try {
            final Properties properties = new Properties();
            final InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(file);
            properties.load(inputStream);
            return properties;
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
