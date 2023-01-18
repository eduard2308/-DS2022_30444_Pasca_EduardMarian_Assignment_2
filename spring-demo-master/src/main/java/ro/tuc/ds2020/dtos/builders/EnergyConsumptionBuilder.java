package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.entities.EnergyConsumption;

public class EnergyConsumptionBuilder {
    private EnergyConsumptionBuilder() {

    }

    public static EnergyConsumptionDTO toEnergyConsumptionDTO(EnergyConsumption energyConsumption) {
        return new EnergyConsumptionDTO(energyConsumption.getId(), energyConsumption.getDeviceId(), energyConsumption.getDate(), energyConsumption.getConsumption());
    }

    public static EnergyConsumption toEntity(EnergyConsumptionDTO energyConsumptionDTO) {
        return new EnergyConsumption(energyConsumptionDTO.getDeviceId(),
                energyConsumptionDTO.getDate(),
                energyConsumptionDTO.getConsumption());
    }
}
