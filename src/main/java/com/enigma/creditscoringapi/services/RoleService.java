package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.entity.enums.ERole;
import com.enigma.creditscoringapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public void saveRole() {
        List<Role> roles = roleRepository.findAll();
        if (roles.size() == 0){
            Role master = new Role(ERole.MASTER);
            Role supervisor = new Role(ERole.SUPERVISOR);
            Role staff = new Role(ERole.STAFF);

            roleRepository.save(master);
            roleRepository.save(supervisor);
            roleRepository.save(staff);
        }
    }

    public Role findRoleByName(ERole name) {
        return roleRepository.findByName(name).orElseThrow(()->new RuntimeException("Error: yRole is not found"));
    }
}
