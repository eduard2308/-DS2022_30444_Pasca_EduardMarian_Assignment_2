package ro.tuc.ds2020.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.EnergyConsumptionService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "/energy")
public class EnergyConsumptionController {
    private final EnergyConsumptionService energyConsumptionService;

    public EnergyConsumptionController(EnergyConsumptionService energyConsumptionService) {
        this.energyConsumptionService = energyConsumptionService;
    }

    @GetMapping()
    public ResponseEntity<List<EnergyConsumptionDTO>> getConsumption() {
        List<EnergyConsumptionDTO> dtos = energyConsumptionService.findConsumption();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/allConsumption/{userId}")
    public ResponseEntity<List<EnergyConsumptionDTO>> getConsumptionByUserId(@PathVariable int userId) {
        List<EnergyConsumptionDTO> dtos = energyConsumptionService.getConsumptionByUserId(userId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
