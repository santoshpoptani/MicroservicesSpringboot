package com.ns.notificationservice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class NotificationService {
    public static void main(String[] args) {
        SpringApplication.run(NotificationService.class, args);
    }

    @KafkaListener(topics = "notificationTopic")
    public void handleMessage(OrderPlacedEvent orderPlacedEvent)
    {
        //business Logic like : send email notification
        log.info("Order placed your Id is - {}" ,orderPlacedEvent.getOrderNumber());
    }
}
