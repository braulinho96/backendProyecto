package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserEntity getUserById(Long id){ return userRepository.findById(id).get(); }
    public UserEntity getUserByRut(String rut){
        return userRepository.findByRut(rut);
    }
    public UserEntity saveUser(UserEntity user) {

        if (userRepository.findByRut(user.getRut()) != null) {
            throw new IllegalArgumentException("The user is already in the database");
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) throws Exception {
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public UserEntity validateUserLogin(String rut, String password) {
        UserEntity user = getUserByRut(rut);
        if (user != null && user.isSolicitude_state() && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }
}
