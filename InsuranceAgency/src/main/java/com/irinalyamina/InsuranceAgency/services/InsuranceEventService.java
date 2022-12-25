package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.repositories.InsuranceEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class InsuranceEventService {

    private final InsuranceEventRepository insuranceEventRepository;

    public InsuranceEventService(InsuranceEventRepository insuranceEventRepository) {
        this.insuranceEventRepository = insuranceEventRepository;
    }

    public List<InsuranceEvent> list() {
        return insuranceEventRepository.findAll();
    }

    public InsuranceEvent getById(Long id) {
        return insuranceEventRepository.findById(id).get();
    }

    public InsuranceEvent create(InsuranceEvent insuranceEvent) {
        return insuranceEventRepository.save(insuranceEvent);
    }

    public void delete(Long id) {
        insuranceEventRepository.deleteById(id);
    }

    public LocalDate searchMaxIncidentDateByPolicyId(Long policyId) {
        return insuranceEventRepository.searchMaxIncidentDateByPolicyId(policyId);
    }
}