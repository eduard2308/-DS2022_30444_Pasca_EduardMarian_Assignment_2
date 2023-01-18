package ro.tuc.ds2020.dtos;

import java.time.LocalDateTime;
import java.util.Date;

public class EnergyConsumptionDTO {
    private int id;

    private int deviceId;

    private Date date;

    private float consumption;

    public EnergyConsumptionDTO() {

    }

    public EnergyConsumptionDTO(int id, int deviceId, Date date, float consumption) {
        this.id = id;
        this.deviceId = deviceId;
        this.date = date;
        this.consumption = consumption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getConsumption() {
        return consumption;
    }

    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }
}
