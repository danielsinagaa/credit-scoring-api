package com.enigma.creditscoringapi.services;

import com.enigma.creditscoringapi.entity.NeedType;
import com.enigma.creditscoringapi.repository.NeedTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeedTypeService extends AbstractService<NeedType, String> {

    @Autowired
    NeedTypeRepository repository;

    @Autowired
    public NeedTypeService(NeedTypeRepository repository) {
        super(repository);
    }

    public void softDelete(String id) {
        repository.softDelete(id);
    }

}
