package ro.tuc.ds2020.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Device implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "maxHourlyEnergyConsumption", nullable = false)
    private float maxHourlyEnergyConsumption;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @OneToMany(mappedBy = "device", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<EnergyConsumption> energyConsumptionArrayList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    public Device() {

    }

    public Device(String name, String description, String address, float maxHourlyEnergyConsumption, int userId) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getMaxHourlyEnergyConsumption() {
        return maxHourlyEnergyConsumption;
    }

    public void setMaxHourlyEnergyConsumption(float maxHourlyEnergyConsumption) {
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
    }

    public List<EnergyConsumption> getEnergyConsumptionArrayList() {
        return energyConsumptionArrayList;
    }

    public void setEnergyConsumptionArrayList(List<EnergyConsumption> energyConsumptionArrayList) {
        this.energyConsumptionArrayList = energyConsumptionArrayList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
