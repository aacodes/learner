# Real time recommendation on search ranking based on user clicks

This project is for educational purpose only:

We use [Spring MVC](https://spring.io/guides/gs/serving-web-content/) to build a webapp with a simple search page. The page allows user to search documents from a search engine [solr](https://lucene.apache.org/solr/guide/6_6/installing-solr.html) index. We publish user's search analytics like clicks to a messaging service [kafka](https://kafka.apache.org/). Kafka consumers then read these messages and re-construct the documents and batch update them in solr. The subsequent searches are then re-ranked.

# Prerequisite: 
- Spring MVC
- Kafka
- Solr
- jQuery

# Guide
Build and run the project from the root directory `learner/`.

NOTE: The indexer jar file needs to be run first so that it can start consuming messages as soon as they are published in the target topic by the learner app. More explanation in the summary section below.

```
mvn clean package
java -jar indexer/target/indexer-0.1.0.jar
java -jar learner/target/learner-0.1.0.jar
```
**Kafka Setup:** [More info](https://kafka.apache.org/quickstart)
This setup assumes you have already setup a zookeeper server with client port `2181`. Create a topic named “solr-vehicles-topic”
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic solr-vehicles-topic
```

List the topics:
```
bin/kafka-topics.sh --list --zookeeper localhost:2181
```

**# Solr Setup:** [More info](https://lucene.apache.org/solr/guide/7_5/installing-solr.html)
Create a collection named "vehicles" with the default configsets.

Add the following to the solr schema ("managed-schema"):
```
 <field name="id" type="string" indexed="true" stored="true" required="true" multiValued="false" />
    <field name="type" type="text_general" indexed="true" stored="true" multiValued="false" />
    <field name="brand" type="text_general" indexed="true" stored="true" multiValued="false" />
    <!-- a field to keep track of the click count -->
    <field name="ckcount" type="pint" indexed="true" stored="true" />	
    <field name="search" type="text_general" indexed="true" stored="true" multiValued="true" />
   
    <copyField source="type" dest="search" maxChars="25000" />
    <copyField source="brand" dest="search" maxChars="25000" />
```
Add the `deftType=edismax` and the default search field `df=search` and boost on the `ckcount`(click count)  to the solrconfig.xml. Portion of the `/select` request handler:
```
 <requestHandler name="/select" class="solr.SearchHandler">
    <lst name="defaults">
      <str name="echoParams">explicit</str>
      <int name="rows">10</int>
      <str name="defType">edismax</str>
      <str name="bf">ckcount^2</str>
      <str name="df">search</str>
```

Index the JSON document under "data" directory:

```./bin/post -c vehicles data/vehicles_data.json ```

Open the browser to the search page:

```
http://localhost:8080
```
# Summary
By default if there are no search keywords the page will bring up all the documents currently indexed in solr. Search terms are matched against the "type" and "brand" of vehicle e.g. search by "toyota". To search using id use e.g. "id:A-2". Click on any relevant search results and the number of clicks will increase by 1. Click on search again with the same search term and notice that the previously clicked result is ranked higher up in the result set. Under the hood when a user clicks on a row/result we publish the document with the click count in a kafka topic (*solr-vehicles-topic*). The indexer app consumes the messages (or documents) from the topic and put it in a buffer. Once the buffer is filled we post the documents to solr. The length of the buffer can be specified beforehand. Currently the buffer size is set to 3. In other words on every 4th click the previous 3 documents will be batch update in solr. Search is then boosted based on the click counts.

Note: There are other ways to re-rank search results. There is a learning to rank module [LTR](https://lucene.apache.org/solr/guide/7_6/learning-to-rank.html) in solr which runs machine learned ranking model in solr.
