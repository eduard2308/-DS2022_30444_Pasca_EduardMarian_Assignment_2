package ro.tuc.ds2020.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class EnergyConsumption implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "device_id")
    private int deviceId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "consumption", nullable = false)
    private float consumption;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "device_id", insertable = false, updatable = false)
    private Device device;

    public EnergyConsumption() {

    }

    public EnergyConsumption(int deviceId, Date date, float consumption) {
        this.deviceId = deviceId;
        this.date = date;
        this.consumption = consumption;
    }

    public int getId() {
        return id;
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
