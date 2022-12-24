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

    public List<PersonAllowedToDrive> list() {
        return personAllowedToDriveRepository.findAll();
    }

    public PersonAllowedToDrive getById(Long id) {
        return personAllowedToDriveRepository.findById(id).get();
    }

    public PersonAllowedToDrive create(PersonAllowedToDrive personAllowedToDrive) {
        return personAllowedToDriveRepository.save(personAllowedToDrive);
    }

    public void edit(PersonAllowedToDrive personAllowedToDrive) {
        personAllowedToDriveRepository.save(personAllowedToDrive);
    }

    public void deleteById(Long id) {
        personAllowedToDriveRepository.deleteById(id);
    }

    public boolean checkDrivingLicence(String drivingLicence) {
        return personAllowedToDriveRepository.existsByDrivingLicence(drivingLicence);
    }

    public boolean checkDrivingLicenceExceptId(Long id, String drivingLicence) {
        return personAllowedToDriveRepository.existsByDrivingLicenceExceptId(id, drivingLicence);
    }
}