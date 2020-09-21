package com.justnansuri.syncupbot.controller;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.justnansuri.syncupbot.model.User;
import com.justnansuri.syncupbot.repository.UserRepository;
import com.justnansuri.syncupbot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    /**
     * getAllusers
     * @return
     */

    @GetMapping("/users")
    public List <User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * getuserById
     * @param userId
     * @return
     * @throws ResourceNotFoundException
     */

    @GetMapping("/users/{id}")
    public ResponseEntity <User> getUserById(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id : " + userId));
        return ResponseEntity.ok().body(user);
    }

    /**
     *
     * @param user
     * @return
     */

    @PostMapping("/users")
    public User createUser(@Validated @RequestBody User user) {
        return userRepository.save(user);
    }

    /**
     * updateUser
     * @param userId
     * @param userDetails
     * @return
     * @throws ResourceNotFoundException
     */

    @PutMapping("/users/{id}")
    public ResponseEntity <User> updateUser(@PathVariable(value = "id") Long userId,
                                                      @Validated @RequestBody User userDetails) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + userId));

        user.setEmailId(userDetails.getEmailId());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * deleteUser
     * @param userId
     * @return
     * @throws ResourceNotFoundException
     */

    @DeleteMapping("/users/{id}")
    public Map <String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user not found for this id :: " + userId));

        userRepository.delete(user);
        Map < String, Boolean > response = new HashMap < > ();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
