package org.aacodes.learner.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aacodes.learner.core.KafkaClientFactory;
import org.aacodes.learner.dto.VehicleDto;
import org.aacodes.learner.services.MessagingService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessagingServiceImpl implements MessagingService {

    private KafkaClientFactory kafkaClientFactory;

    @Autowired
    public MessagingServiceImpl(KafkaClientFactory kafkaClientFactory) {
        this.kafkaClientFactory = kafkaClientFactory;
    }

    @Override
    public void publish(VehicleDto vehicle, String topic) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String vehicleStr = mapper.writeValueAsString(vehicle);
            System.out.println("vehicle in string: " + vehicleStr);
            ProducerRecord<String, String> vehicleRecord = new ProducerRecord<>(topic, vehicle.getId(), vehicleStr);
            this.kafkaClientFactory.getProducer().send(vehicleRecord);
            System.out.println("published to topic");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
