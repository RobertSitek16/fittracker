package com.fittracker.user.service;

import com.fittracker.user.entity.User;
import com.fittracker.user.exception.UserNotFoundException;
import com.fittracker.user.hateoas.UserModel;
import com.fittracker.user.hateoas.UserModelAssembler;
import com.fittracker.user.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserModelAssembler userModelAssembler;

    public UserService(UserRepository userRepository, UserModelAssembler userModelAssembler) {
        this.userRepository = userRepository;
        this.userModelAssembler = userModelAssembler;
    }

    public CollectionModel<UserModel> getAll() {
        List<User> users = userRepository.findAll();
        return userModelAssembler.toCollectionModel(users);
    }

    public UserModel getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return userModelAssembler.toModel(user);
    }

    public UserModel createUser(User user) {
        User savedUser = userRepository.save(user);
        return userModelAssembler.toModel(savedUser);
    }

    public UserModel updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        return userModelAssembler.toModel(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

}
