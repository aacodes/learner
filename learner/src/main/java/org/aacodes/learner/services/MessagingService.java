package org.aacodes.learner.services;

import org.aacodes.learner.dto.VehicleDto;

public interface MessagingService {
    void publish(VehicleDto vehicle, String topic);
}
