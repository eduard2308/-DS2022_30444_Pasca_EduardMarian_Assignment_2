package ro.tuc.ds2020.dtos;

import java.util.Objects;
import java.util.UUID;

public class UserDTO {
    private int id;
    private String email;
    private String password;
    private String role;

    public UserDTO() {
    }

    public UserDTO(int id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) { this.password = password;}

    public String getRole() { return role; }

    public void setRole(String role) { this.role = role; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(email, userDTO.email) &&
                Objects.equals(password, userDTO.password) &&
                Objects.equals(role, userDTO.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role);
    }
}
