package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.Role;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.RoleRequest;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.RoleRepository;
import com.enigma.creditscoringapi.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/role")
@RestController
public class RoleController {
    @Autowired
    RoleService service;

    @Autowired
    RoleRepository repository;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public ResponseMessage allRole(PageSearch search){
        Page<Role> roles = service.findAll(new Role(), search.getPage(), search.getSize(), search.getSort());

        PagedList<Role> rolePagedList = new PagedList(roles.toList(), roles.getNumber(), roles.getSize(), roles.getTotalElements());

        return ResponseMessage.success(rolePagedList);
    }

    @GetMapping("/{id}")
    public ResponseMessage roleById(@PathVariable String id){
        Role role = service.findById(id);

        if (role == null){
            throw new EntityNotFoundException();
        }

        return ResponseMessage.success(role);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage deleteRole(@PathVariable String id){
        Role role = service.findById(id);

        if (role == null){
            throw new EntityNotFoundException();
        }

        role.setName(role.getName() + "*");

        repository.softDelete(id);

        return ResponseMessage.success(role);
    }

    @PostMapping
    public ResponseMessage addRole(@RequestBody RoleRequest request){
        Role entity = modelMapper.map(request, Role.class);
        entity.setName(entity.getName().toUpperCase());

        service.save(entity);

        return ResponseMessage.success(entity);
    }
}
