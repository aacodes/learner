package org.aacodes.learner.repository.impl;

import org.aacodes.learner.SearchClientFactory;
import org.aacodes.learner.exception.mapper.NoSearchResultsException;
import org.aacodes.learner.repository.BasicSearchOperation;
import org.aacodes.learner.response.SearchResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.MapSolrParams;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class SolrSearchOperationImpl implements BasicSearchOperation {

    private SearchClientFactory searchClientFactory;

    private SolrSearchOperationImpl(SearchClientFactory factory) {
        this.searchClientFactory = factory;
    }

    @Override
    public <T> SearchResponse<T> doSearch(String collection, Map<String, String> queryParams,
                                          Class<T> clazz) {
        HttpSolrClient client = this.searchClientFactory.getSolrHttpClient();
        MapSolrParams solrParams = new MapSolrParams(queryParams);
        QueryResponse response = null;
        try {
            response = client.query(collection, solrParams);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }
        List<T> results = Optional.ofNullable(response)
                .map(p -> p.getBeans(clazz))
                .orElseThrow(NoSearchResultsException::new);
        SearchResponse<T> result = new SearchResponse<>();
        result.setResponse(results);
        return result;
    }

}
