package org.aacodes.indexer.service;


public interface MessagingService {

    void consumeAndIndex(String topic);

}
