package ro.tuc.ds2020.services;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.WebSocketController;
import ro.tuc.ds2020.dtos.EnergyConsumptionDTO;
import ro.tuc.ds2020.dtos.builders.EnergyConsumptionBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.EnergyConsumption;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.EnergyConsumptionRepository;
import ro.tuc.ds2020.websockets.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnergyConsumptionService {
    private final DeviceRepository deviceRepository;

    private final EnergyConsumptionRepository energyConsumptionRepository;

    private final WebSocketController webSocketController;

    public void save(String energyConsString){
        try {
            JSONObject jsonObject = new JSONObject(energyConsString);
            String deviceIdString = jsonObject.getString("iddevice");
            String energyCons = jsonObject.getString("energyCons");
            String dtInsertString = jsonObject.getString("dtInsert");
            int deviceId = Integer.parseInt(deviceIdString);
            float consumption = Float.parseFloat(energyCons);
            EnergyConsumption newEnergyConsumption = new EnergyConsumption();
            newEnergyConsumption.setConsumption(consumption);
            newEnergyConsumption.setDeviceId(deviceId);
            String pattern = "dd-MM-yyyy hh";
            newEnergyConsumption.setDate(new SimpleDateFormat(pattern).parse(dtInsertString));
            Device device = deviceRepository.findById(deviceId).orElse(null);
            newEnergyConsumption.setDevice(device);
            energyConsumptionRepository.save(newEnergyConsumption);
            computeHourlyConsumption(device);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void computeHourlyConsumption(Device device) throws Exception {
        String pattern = "dd-MM-yyyy hh";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = sdf.parse(sdf.format(new Date()));
        Optional<Float> sum = energyConsumptionRepository.computeSumForDate(device.getId());
        if(sum.get() > device.getMaxHourlyEnergyConsumption()) {
            System.out.println("Device consumption reached!!!!");
            Message message = Message.builder()
                    .text("Limit reached for device " + device.getId())
                    .build();
            webSocketController.notificationFunction(message, device.getUserId());
        }
    }

    public List<EnergyConsumptionDTO> findConsumption() {
        List<EnergyConsumption> energyConsumptionList = energyConsumptionRepository.findAll();
        return energyConsumptionList.stream()
                .map(EnergyConsumptionBuilder::toEnergyConsumptionDTO)
                .collect(Collectors.toList());
    }

    public List<EnergyConsumptionDTO> getConsumptionByUserId(Integer userId) {
        List <EnergyConsumption> energyConsumptionList =  energyConsumptionRepository.findAllByUser(userId);
        System.out.println(energyConsumptionList.size() + "             ");
        return energyConsumptionList.stream()
                .map(EnergyConsumptionBuilder::toEnergyConsumptionDTO)
                .collect(Collectors.toList());
    }
}
