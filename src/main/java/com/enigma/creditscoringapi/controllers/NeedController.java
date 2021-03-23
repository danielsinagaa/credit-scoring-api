package com.enigma.creditscoringapi.controllers;

import com.enigma.creditscoringapi.entity.NeedType;
import com.enigma.creditscoringapi.models.NeedTypeRequest;
import com.enigma.creditscoringapi.models.pages.PageSearch;
import com.enigma.creditscoringapi.models.responses.ResponseMessage;
import com.enigma.creditscoringapi.services.NeedTypeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/need")
@RestController
public class NeedController {
    @Autowired
    private NeedTypeService service;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseMessage add(@RequestBody NeedTypeRequest request){
        NeedType needType = new NeedType(request.getType());

        return ResponseMessage.success(needType);
    }

    @PutMapping("/{id}")
    public ResponseMessage edit(@PathVariable String id, @RequestBody NeedTypeRequest request){
        NeedType needType = service.findById(id);

        needType.setType(request.getType());
        service.save(needType);

        return ResponseMessage.success(needType);
    }

    @GetMapping("/{id}")
    public ResponseMessage byId(@PathVariable String id){
        return ResponseMessage.success(service.findById(id));
    }

    @GetMapping
    public ResponseMessage all(PageSearch search){
        Page<NeedType> datas = service.findAll(new NeedType(), search.getPage(), search.getSize(), search.getSort());

        return ResponseMessage.success(datas);
    }
}
