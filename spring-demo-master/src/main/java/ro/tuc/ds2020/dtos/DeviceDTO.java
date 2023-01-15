package ro.tuc.ds2020.dtos;


public class DeviceDTO {
    private int id;

    private String name;

    private String description;

    private String address;

    private float maxHourlyEnergyConsumption;

    private int userId;

    public DeviceDTO() {

    }

    public DeviceDTO(int id, String name, String description, String address, float maxHourlyEnergyConsumption, int userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.maxHourlyEnergyConsumption = maxHourlyEnergyConsumption;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Objects.equals(name, deviceDTO.name) &&
                Objects.equals(description, deviceDTO.description) &&
                Objects.equals(address, deviceDTO.address) &&
                Objects.equals(maxHourlyEnergyConsumption, deviceDTO.maxHourlyEnergyConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, address, maxHourlyEnergyConsumption);
    }*/
}
