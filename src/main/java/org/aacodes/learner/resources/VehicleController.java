package org.aacodes.learner.resources;

import org.aacodes.learner.dto.VehicleDto;
import org.aacodes.learner.model.VehicleModel;
import org.aacodes.learner.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class VehicleController {

    private VehicleService vehicleService;

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    @ResponseBody
    public List<VehicleModel> searchVehicle(@RequestParam(name = "q", defaultValue = "*:*") String query) {
        System.out.println("Search using term: " + query);
        return this.vehicleService.search(query);
    }


    @PostMapping("/vehicles")
    @ResponseBody
    public void updateVehicle(@RequestBody VehicleDto vehicleDto) {
        System.out.println("updating doc: " + vehicleDto);
        this.vehicleService.update(vehicleDto);
    }
}
