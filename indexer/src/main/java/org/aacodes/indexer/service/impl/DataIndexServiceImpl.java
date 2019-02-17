package org.aacodes.indexer.service.impl;

import org.aacodes.indexer.factory.SearchClientFactory;
import org.aacodes.indexer.dto.VehicleDto;
import org.aacodes.indexer.service.DataIndexService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DataIndexServiceImpl implements DataIndexService {

    private SearchClientFactory searchClientFactory;

    @Autowired
    public DataIndexServiceImpl(SearchClientFactory searchClientFactory) {
        this.searchClientFactory = searchClientFactory;
    }

    public boolean updateIndex(String collection, List<VehicleDto> vehicles) {
        SolrClient client = this.searchClientFactory.getSolrHttpClient();

        UpdateResponse response = null;
        try {
            client.addBeans(collection, vehicles);
            response = client.commit(collection);
        } catch (SolrServerException | IOException e) {
            e.printStackTrace();
        }

        return response != null && response.getStatus() == 0;
    }
}
