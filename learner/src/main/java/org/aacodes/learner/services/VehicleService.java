package org.aacodes.learner.services;

import org.aacodes.learner.dto.VehicleDto;
import org.aacodes.learner.model.VehicleModel;

import java.util.List;

public interface VehicleService {

    List<VehicleModel> search(String queryTerms);

    void update(VehicleDto vehicleDto);
}
