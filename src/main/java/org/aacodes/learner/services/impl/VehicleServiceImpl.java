package org.aacodes.learner.services.impl;

import org.aacodes.learner.common.SearchConstants;
import org.aacodes.learner.dto.VehicleDto;
import org.aacodes.learner.model.VehicleModel;
import org.aacodes.learner.repository.BasicSearchOperation;
import org.aacodes.learner.response.SearchResponse;
import org.aacodes.learner.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VehicleServiceImpl implements VehicleService {

    private BasicSearchOperation searchOperation;

    @Autowired
    public VehicleServiceImpl(BasicSearchOperation searchOperation) {
        this.searchOperation = searchOperation;
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
        this.searchOperation.updateIndex(SearchConstants.VEHICLES_COLLECTION, vehicleDto);
    }

}
