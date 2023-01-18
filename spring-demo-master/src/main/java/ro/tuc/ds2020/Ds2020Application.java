package ro.tuc.ds2020;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.validation.annotation.Validated;
import ro.tuc.ds2020.services.EnergyConsumptionService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.TimeZone;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {
    private final static String QUEUE_NAME = "ds-assign2";
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, TimeoutException {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+2"));
        EnergyConsumptionService energyConsumptionService = SpringApplication.run(Ds2020Application.class, args).getBean(EnergyConsumptionService.class);

        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setUsername("ycnykvdq");
        connectionFactory.setPassword("l6SYXBpW0XpnU1_NCMqJdrae4gGL6X4R");
        connectionFactory.setPort(5672);
        connectionFactory.setUri("amqps://ycnykvdq:l6SYXBpW0XpnU1_NCMqJdrae4gGL6X4R@cow.rmq2.cloudamqp.com/ycnykvdq");

        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(message);
            energyConsumptionService.save(message);
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
}
