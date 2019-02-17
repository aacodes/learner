package org.aacodes.learner.repository;

import org.aacodes.learner.response.SearchResponse;

import java.util.Map;

public interface BasicSearchOperation {

    <T> SearchResponse<T> doSearch(String collection, Map<String, String> queryParams, Class<T> clazz);

}
