package org.aacodes.indexer;

import org.aacodes.indexer.common.TopicCollection;
import org.aacodes.indexer.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.aacodes.indexer")
public class IndexerMachine {

    private MessagingService messagingService;

    @Autowired
    public IndexerMachine(MessagingService messagingService) {
        this.messagingService = messagingService;
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(IndexerMachine.class);
        applicationContext.refresh();
        IndexerMachine indexerMachine = applicationContext.getBean(IndexerMachine.class);
        indexerMachine.start(args);
    }

    public void start(String[] args) {
        System.out.println("Start reading from kafka topic and indexing to solr");
        this.messagingService.consumeAndIndex(TopicCollection.VEHICLES.getTopic());
    }
}
