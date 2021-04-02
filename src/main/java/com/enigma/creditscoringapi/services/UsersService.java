package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Users;
import com.enigma.creditscoringapi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<Users> findAllVerified(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return userRepository.findAllVerified(PageRequest.of(page, size, sort));

    }

    public Page<Users> findAllNotVerified(int page, int size, Sort.Direction direction) {
        Sort sort = Sort.Direction.DESC.equals(direction) ?
                Sort.by(direction, "createdDate") : Sort.by("createdDate");
        return userRepository.findAllNotVerified(PageRequest.of(page, size, sort));

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
