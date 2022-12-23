package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.repositories.InsuranceEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsuranceEventService {

    private final InsuranceEventRepository insuranceEventRepository;

    public InsuranceEventService(InsuranceEventRepository insuranceEventRepository) {
        this.insuranceEventRepository = insuranceEventRepository;
    }

    public List<InsuranceEvent> list(){
        return insuranceEventRepository.findAll();
    }

    public InsuranceEvent getById(Long id) {
        return insuranceEventRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        insuranceEventRepository.deleteById(id);
    }
}