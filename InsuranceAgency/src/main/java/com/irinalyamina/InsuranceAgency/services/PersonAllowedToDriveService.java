package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.PersonAllowedToDrive;
import com.irinalyamina.InsuranceAgency.repositories.PersonAllowedToDriveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonAllowedToDriveService {

    private final PersonAllowedToDriveRepository personAllowedToDriveRepository;

    public PersonAllowedToDriveService(PersonAllowedToDriveRepository personAllowedToDriveRepository) {
        this.personAllowedToDriveRepository = personAllowedToDriveRepository;
    }

    public List<PersonAllowedToDrive> list(){
        return personAllowedToDriveRepository.findAll();
    }

    public PersonAllowedToDrive getById(Long id) {
        return personAllowedToDriveRepository.findById(id).get();
    }
}