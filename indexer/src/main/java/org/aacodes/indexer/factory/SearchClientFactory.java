package org.aacodes.indexer.factory;

import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/application.properties")
public class SearchClientFactory {

    @Autowired
    Environment env;

    @Bean
    public HttpSolrClient getSolrHttpClient() {
        return new HttpSolrClient.Builder()
                .withBaseSolrUrl(env.getProperty("solr.connection"))
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }
}
