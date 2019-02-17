package org.aacodes.learner.services.impl;

import org.aacodes.learner.common.SearchConstants;
import org.aacodes.learner.common.TopicCollection;
import org.aacodes.learner.dto.VehicleDto;
import org.aacodes.learner.model.VehicleModel;
import org.aacodes.learner.repository.BasicSearchOperation;
import org.aacodes.learner.response.SearchResponse;
import org.aacodes.learner.services.MessagingService;
import org.aacodes.learner.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VehicleServiceImpl implements VehicleService {

    private BasicSearchOperation searchOperation;
    private MessagingService messagingService;


    @Autowired
    public VehicleServiceImpl(BasicSearchOperation searchOperation, MessagingService messagingService) {
        this.searchOperation = searchOperation;
        this.messagingService = messagingService;
    }

    @Override
    public List<VehicleModel> search(String queryTerms) {
        final Map<String, String> queryParamMap = new HashMap<>();
        queryParamMap.put("q", queryTerms);
        queryParamMap.put("fl", "id, type, brand, ckcount, score");

        SearchResponse<VehicleModel> response =
                this.searchOperation.doSearch(SearchConstants.VEHICLES_COLLECTION, queryParamMap, VehicleModel.class);
        return response.getResponse();
    }

    public void update(VehicleDto vehicleDto) {
        this.messagingService.publish(vehicleDto, TopicCollection.VEHICLES.getTopic());
    }

}
