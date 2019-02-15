package org.aacodes.learner;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchClientFactory {

    @Value("${solr.connection}")
    String solrLocation;

    @Bean
    public HttpSolrClient getSolrHttpClient() {
        return new HttpSolrClient.Builder()
                .withBaseSolrUrl(solrLocation)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }
}
