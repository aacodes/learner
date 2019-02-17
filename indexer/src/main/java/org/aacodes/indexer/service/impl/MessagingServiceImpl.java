package org.aacodes.indexer.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aacodes.indexer.common.MessagingConstants;
import org.aacodes.indexer.common.TopicCollection;
import org.aacodes.indexer.dto.VehicleDto;
import org.aacodes.indexer.factory.MessagingClientFactory;
import org.aacodes.indexer.service.DataIndexService;
import org.aacodes.indexer.service.MessagingService;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessagingServiceImpl implements MessagingService {

    private DataIndexService dataIndexService;
    private MessagingClientFactory clientFactory;

    @Autowired
    public MessagingServiceImpl(DataIndexService dataIndexService, MessagingClientFactory clientFactory) {
        this.dataIndexService = dataIndexService;
        this.clientFactory = clientFactory;
    }

    @Override
    public void consumeAndIndex(String topic) {
        try (Consumer<String, String> consumer = this.clientFactory.getConsumer()) {
            consumer.subscribe(Collections.singletonList(topic));
            List<VehicleDto> vehicles = new ArrayList<>();
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        VehicleDto vehicleDto = mapper.readValue(record.value(), VehicleDto.class);
                        vehicles.add(vehicleDto);
                        System.out.println("Currently in topic: \n" + vehicleDto);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (vehicles.size() >= MessagingConstants.MIN_BATCH_SIZE) {
                    if (this.dataIndexService.updateIndex(TopicCollection.VEHICLES.getCollection(), vehicles)) {
                        System.out.println("Committed to solr. Number of docs: "
                                + vehicles.size() + " now committing sync");
                        consumer.commitSync();
                        vehicles.clear();
                    }
                }
            }
        }
    }

}
