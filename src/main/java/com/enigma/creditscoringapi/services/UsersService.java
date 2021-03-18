package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService extends AbstractService<Users, String > {

    public UsersService(UsersRepository repository) {
        super(repository);
    }

    @Autowired
    UsersRepository userRepository;

    public void softDelete(String id) {
        userRepository.softDelete(id);
    }

    public Boolean existByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public Boolean existByEmail(String username){
        return userRepository.existsByEmail(username);
    }

    public Users findByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    public Users findByToken(String token) {
        return userRepository.findByVerifiedToken(token);
    }
}
