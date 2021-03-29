package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService extends AbstractService<Role, String>{

    @Autowired
    public RoleService(RoleRepository repository) {
        super(repository);
    }

    @Autowired
    RoleRepository roleRepository;

    public List<String> allRoleName(){
        return roleRepository.findAll().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(()->new RuntimeException("Role is not found"));
    }

    public List<String> findAllInputCustomer(){
        return roleRepository.findAllInputCustomer().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllReadAllCustomer(){
        return roleRepository.findAllReadAllCustomer().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllInputTransaction(){
        return roleRepository.findAllInputTransaction().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findReadAllTransaction(){
        return roleRepository.findReadAllTransaction().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllApproveTransaction(){
        return roleRepository.findAllApproveTransaction().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllReadAllReport(){
        return roleRepository.findAllReadAllReport().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllReadAllReportByTransaction(){
        return roleRepository.findAllReadAllReportByTransaction().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllMaster(){
        return roleRepository.findAllMaster().stream()
                .map(Role::getName)
                .collect(Collectors.toList());
    }
}
