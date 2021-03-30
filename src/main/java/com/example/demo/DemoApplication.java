package com.example.demo;

import com.rabbitmq.client.*;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

@SpringBootApplication
public class DemoApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        ConnectionFactory factory = new ConnectionFactory();
        Channel ch = factory.newConnection().createChannel();

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-single-active-consumer", true);
        ch.queueDeclare("my-queue", false, false, false, arguments);

        for (int i = 0; i < 10; i++) {
            ch.basicPublish("", "my-queue", null, String.valueOf(i).getBytes());
        }

        Thread t1 = new Thread(() -> {
            System.out.println("First thread started!");
            try {
                Channel ch1 = factory.newConnection().createChannel();
                ch1.basicQos(1);
                DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println("Consumer " + consumerTag + " received '" + message + "'");
                    try{ Thread.sleep(5000); }catch (Exception e) {}
                    ch1.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    if (Integer.parseInt(message) == 7) {
                        try{
                            ch1.close();
                        }catch (Exception e) {}
                        System.out.println(consumerTag +" consumer died!");
                    }
                };
                ch1.basicConsume("my-queue", deliverCallback,
                        consumerTag -> {});
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        });
        t1.start();

        new Thread(() -> {
            System.out.println("Second thread started!");

            try {
                Channel ch2 = factory.newConnection().createChannel();
                ch2.basicQos(1);
                ch2.basicConsume("my-queue", (consumerTag, delivery) -> {
                            String message = new String(delivery.getBody(), "UTF-8");
                            System.out.println("Consumer " + Thread.currentThread().getName() + " " + consumerTag + " received '" + message + "'");
                            try{ Thread.sleep(5000); }catch (Exception e) {}
                            ch2.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        },
                        consumerTag -> {});
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }


        }).start();
    }

}
