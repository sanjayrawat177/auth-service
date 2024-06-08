package com.sr.projects.auth_service.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sr.projects.auth_service.dto.request.UserRequest;
import com.sr.projects.auth_service.entity.Users;
import com.sr.projects.auth_service.repository.UserRepository;
import com.sr.projects.auth_service.security.PBKDF2Impl;
import com.sr.projects.auth_service.security.interfaces.PasswordHash;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public void createUser(UserRequest incomingRequest) {
        Users userEntityObj = modelMapper.map(incomingRequest, Users.class);
        userEntityObj.setPassword(generateHashPassword(incomingRequest.getPassword()));
        userRepository.save(userEntityObj);

    }

    private String generateHashPassword(String password) {
        PasswordHash hash = new PBKDF2Impl();
        return hash.generateHash(password);

    }

    public boolean checkUser(UserRequest user) {
        String generatedPassword = generateHashPassword(user.getPassword());
        Optional<Users> byUserNameAndPassword = userRepository.findByUserNameAndPassword(user.getUserName(), generatedPassword);
        if(byUserNameAndPassword.isPresent()) {
            return true;
        }
        return false;
    }


}
