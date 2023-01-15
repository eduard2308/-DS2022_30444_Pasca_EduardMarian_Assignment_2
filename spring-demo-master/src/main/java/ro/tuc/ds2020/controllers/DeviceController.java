package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.UUID;

import static org.hibernate.usertype.DynamicParameterizedType.ENTITY;

@CrossOrigin
@RestController
@RequestMapping("/devices")
public class DeviceController {

    DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(value = "/admin")
    public ResponseEntity<Integer> create(@RequestBody DeviceDTO deviceDTO) {
        int deviceId = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceId, HttpStatus.CREATED);
    }


    @GetMapping(value = "/admin/{deviceId}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("deviceId") int deviceId) {
        DeviceDTO dto = DeviceBuilder.toDeviceDTO(deviceService.findDeviceById(deviceId));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @DeleteMapping("/admin/{deviceId}")
    public ResponseEntity<?> deleteDevice(@PathVariable("deviceId") int deviceId){
        deviceService.delete(deviceId);
        return ResponseEntity.ok().body(new UserDTO());
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PatchMapping("/admin/{deviceId}")
    public DeviceDTO edit(@PathVariable int deviceId, @RequestBody DeviceDTO deviceDTO) {
        return deviceService.edit(deviceId, deviceDTO);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<DeviceDTO>> getDevicesAdmin() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/allDevices/{userId}")
    public ResponseEntity<List<DeviceDTO>> getAllDevicesByUserId(@PathVariable int userId) {
        List<DeviceDTO> dtos = deviceService.getAllDevicesByUserId(userId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }
}
