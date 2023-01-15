package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class LoginController {
    UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDTO) {
        UserDTO userDTO1 = userService.login(userDTO);
        System.out.println(userDTO1.getEmail() + " " + userDTO1.getPassword() + " " + userDTO1.getRole());
        if(userDTO1 == null) {
            return new ResponseEntity<>(userDTO1, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(userDTO1);
    }
}
