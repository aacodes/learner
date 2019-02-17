package org.aacodes.indexer.service;

import org.aacodes.indexer.dto.VehicleDto;

import java.util.List;

public interface DataIndexService {

    boolean updateIndex(String collection, List<VehicleDto> vehicle);
}
