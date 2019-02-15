package org.aacodes.learner.repository;

import org.aacodes.learner.exception.mapper.NoSearchResultsException;
import org.aacodes.learner.dto.VehicleDto;
import org.aacodes.learner.response.SearchResponse;

import java.util.Map;

public interface BasicSearchOperation {

    <T> SearchResponse<T> doSearch(String collection, Map<String, String> queryParams, Class<T> clazz);

    void updateIndex(String collection, VehicleDto vehicleDto);
}
