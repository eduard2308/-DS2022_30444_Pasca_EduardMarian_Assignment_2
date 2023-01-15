package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUser(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        if(user.isPresent()) {
            return (UserBuilder.toUserDTO(user.get()));
        }
        else return null;
    }

    public User findUserById(int id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Person with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return prosumerOptional.get();
    }

    public int insert(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);
        LOGGER.debug("Person with id {} was inserted in db", user.getId());
        return user.getId();
    }

    public UserDTO login(UserDTO userDTO) {
        Optional<User> user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        return user.map(UserBuilder::toUserDTO).orElse(null);
    }

    public void delete(int userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO edit(int id, UserDTO userDTO) {
        User actUser = findUserById(id);
        actUser.setEmail(userDTO.getEmail());
        return UserBuilder.toUserDTO(
                userRepository.save(actUser)
        );
    }

    public List<Integer> getAllUserIds() {
        return userRepository.findAllIds();
    }

/*    public void update(UserDTO userDTO)
    {
        userRepository.save(UserBuilder.toEntity(userDTO));
    }*/
}
