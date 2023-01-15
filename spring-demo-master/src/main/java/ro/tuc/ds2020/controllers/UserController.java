package ro.tuc.ds2020.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.hibernate.usertype.DynamicParameterizedType.ENTITY;

@RestController
@CrossOrigin
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUsers();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Integer> insertProsumer(@Valid @RequestBody UserDTO userDTO) {
        int userId = userService.insert(userDTO);
        return new ResponseEntity<>(userId, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") int userId) {
        UserDTO dto = UserBuilder.toUserDTO(userService.findUserById(userId));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public UserDTO edit(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.edit(id, userDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId){
        userService.delete(userId);
        return ResponseEntity.ok().body(new UserDTO());
    }

    @GetMapping(value = "/allIds")
    public ResponseEntity<List<Integer>> getAllIds() {
        List<Integer> userIds = userService.getAllUserIds();
        return new ResponseEntity<>(userIds, HttpStatus.OK);
    }
}
