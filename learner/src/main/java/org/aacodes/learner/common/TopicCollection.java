package org.aacodes.learner.common;

public enum TopicCollection {

    VEHICLES("solr-vehicles-topic", "vehicles");

    TopicCollection(String topic, String collection) {
        this.topic = topic;
        this.collection = collection;
    }
    private String topic;
    private String collection;

    public String getTopic() {
        return this.topic;
    }

    public String getCollection() {
        return this.collection;
    }
}
