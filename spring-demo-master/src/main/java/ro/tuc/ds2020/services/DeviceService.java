package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public int insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return device.getId();
    }

    public void delete(int deviceId) {
        deviceRepository.deleteById(deviceId);
    }

    public List<DeviceDTO> findDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public Device findDeviceById(int id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return prosumerOptional.get();
    }

    public DeviceDTO edit(int id, DeviceDTO deviceDTO) {
        Device device = findDeviceById(id);
        device.setName(deviceDTO.getName());
        device.setDescription(deviceDTO.getDescription());
        device.setAddress(deviceDTO.getAddress());
        device.setMaxHourlyEnergyConsumption(deviceDTO.getMaxHourlyEnergyConsumption());
        return DeviceBuilder.toDeviceDTO(
                deviceRepository.save(device)
        );
    }

    public List<DeviceDTO> getAllDevicesByUserId(Integer userId) {
        List <Device> devices =  deviceRepository.findAllByUserId(userId);
        return devices.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }
}
