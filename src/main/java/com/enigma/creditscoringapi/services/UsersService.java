package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService {
    @Autowired
    UsersRepository userRepository;

    public Page<Users> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Page<Users> getAllUserByUserName(Pageable pageable, Users user) {
        return userRepository.findByUsernameContainsOrEmailContains(user.getUsername(),user.getEmail(),pageable);
    }

    public Users getUserById(String id) {
        return userRepository.findById(id).get();
    }

    public Users saveDataUser(Users user) {
        return userRepository.save(user);
    }

    public void deleteDataUser(String id) {
        userRepository.deleteById(id);
    }

    public Users getUserByUsername(String name) {
        Users user =userRepository.findByUsername(name)
                .orElseThrow(()->new UsernameNotFoundException("User Not Found with username: "+name));
        return user;
    }
}
