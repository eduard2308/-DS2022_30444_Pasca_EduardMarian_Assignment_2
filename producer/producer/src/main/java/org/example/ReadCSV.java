package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static java.lang.Thread.sleep;

public class ReadCSV
{
    private final static String QUEUE_NAME = "ds-assign2";
    public static void readFromCsv() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException {
//parsing a CSV file into Scanner class constructor  
        Scanner sc = null;
        try {
            sc = new Scanner(new File("D:\\AN4\\DS\\Demo\\-DS2022_30444_Pasca_EduardMarian_Assignment_2\\producer\\sensor.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        sc.useDelimiter(",");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
            String energyCons = sc.nextLine();
            //System.out.println(energyCons);
            int id = ReadFile.readFromFile();
            String pattern = "dd-MM-yyyy hh";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String date = sdf.format(new Date());
            System.out.println(id+ " " + energyCons + " " + date);
            JSONObject deviceMessage = new JSONObject();
            deviceMessage.put("iddevice",String.valueOf(id));
            deviceMessage.put("energyCons", energyCons);
            deviceMessage.put("dtInsert",date);

            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setUsername("ycnykvdq");
            connectionFactory.setPassword("l6SYXBpW0XpnU1_NCMqJdrae4gGL6X4R");
            connectionFactory.setPort(5672);
            connectionFactory.setUri("amqps://ycnykvdq:l6SYXBpW0XpnU1_NCMqJdrae4gGL6X4R@cow.rmq2.cloudamqp.com/ycnykvdq");

            try (Connection connection = connectionFactory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                channel.basicPublish("", QUEUE_NAME, null, deviceMessage.toString().getBytes(StandardCharsets.UTF_8));
                System.out.println(" [x] Sent '" + deviceMessage + "'");
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (TimeoutException e) {
                throw new RuntimeException(e);
            }
            sleep(1000);
        }
        sc.close();  //closes the scanner
    }
}  