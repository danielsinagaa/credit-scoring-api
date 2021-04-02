package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.NeedType;
import com.enigma.creditscoringapi.exceptions.EntityNotFoundException;
import com.enigma.creditscoringapi.models.NeedTypeRequest;
import com.enigma.creditscoringapi.models.NeedTypeResponse;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.pages.PagedList;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.repository.NeedTypeRepository;
import com.enigma.creditscoringapi.services.NeedTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin
@RequestMapping("/need")
@RestController
public class NeedController {
    @Autowired
    private NeedTypeService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NeedTypeRepository repository;

    @PostMapping
    public ResponseMessage add(@RequestBody NeedTypeRequest request){

        if (repository.existsByType(request.getType().toUpperCase())){
            return new ResponseMessage(400, "loan purpose already exist", "loan purpose already exist");
        }

        NeedType needType = new NeedType(request.getType().toUpperCase());
        service.save(needType);

        return ResponseMessage.success(needType);
    }

    @PutMapping("/{id}")
    public ResponseMessage edit(@PathVariable String id, @RequestBody NeedTypeRequest request){
        NeedType needType = service.findById(id);

        if (needType == null) throw new EntityNotFoundException();

        needType.setType(request.getType().toUpperCase());
        service.save(needType);

        return ResponseMessage.success(needType);
    }

    @DeleteMapping("/{id}")
    public ResponseMessage delete(@PathVariable String id){
        NeedType needType = service.findById(id);

        if (needType == null) throw new EntityNotFoundException();

        needType.setType(needType.getType() + randomPassword());
        service.save(needType);

        service.softDelete(id);

        return ResponseMessage.success(needType);
    }

    @GetMapping("/{id}")
    public ResponseMessage byId(@PathVariable String id){
        NeedType needType = service.findById(id);

        if (needType == null) throw new EntityNotFoundException();

        return ResponseMessage.success(service.findById(id));
    }

    @GetMapping
    public ResponseMessage all(PageSearch search){
        Page<NeedType> entityPage = service.findAll(new NeedType(), search.getPage(), search.getSize(), search.getSort());

        List<NeedType> entities = entityPage.toList();

        List<NeedTypeResponse> responses = entities.stream()
                .map(e -> modelMapper.map(e, NeedTypeResponse.class))
                .collect(Collectors.toList());

        PagedList<NeedTypeResponse> response = new PagedList(responses, entityPage.getNumber(),
                entityPage.getSize(), entityPage.getTotalElements());

        return ResponseMessage.success(response);
    }

    private String randomPassword() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        String saltStr = salt.toString().toLowerCase();
        return saltStr;
    }
}
