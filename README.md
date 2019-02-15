# Real time recommendation on search ranking based on user clicks

This project is for educational purpose only:

We use <a href="https://spring.io/guides/gs/serving-web-content/">Spring MVC</a> to build a webapp with a simple search page. The page allows user to search documents from a search engine (<a href="https://lucene.apache.org/solr/guide/6_6/installing-solr.html">solr</a>) index.

# Prerequisite: 
- Spring MVC
- Solr
- jQuery

# Summary
Build and run the project:
```
mvn clean package
java -jar learner-0.1.0.jar
```
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

By default if there are no search keywords the page will bring up all the documents currently indexed in solr. Search terms are matched against the "type" and "brand" of vehicle e.g. search by "toyota". To search using id use e.g. "id:A-2". Click on any relevant search results and the number of clicks will increase by 1. Click on search again with the same search term and notice that the previously clicked result is ranked higher up in the result set. Under the hood when a user clicks on a row/result a post request is made to solr to update the click count of the corresponding document. Search is then boosted based on the click counts. Note that indexing one document at a time is not optimal as mentioned here in the <a href="https://lucene.apache.org/solr/guide/7_5/using-solrj.html">solr doc</a>

Note: There are other ways to re-rank search results. There is a learning to rank module <a href="https://lucene.apache.org/solr/guide/7_6/learning-to-rank.html">LTR</a> in solr which runs machine learned ranking model in solr.
