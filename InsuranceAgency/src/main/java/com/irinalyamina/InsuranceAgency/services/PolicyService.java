package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.repositories.PolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public List<Policy> list() {
        return policyRepository.findAll();
    }

    public Policy getById(Long id) {
        return policyRepository.findById(id).get();
    }

    public Policy create(Policy policy) {
        return policyRepository.save(policy);
    }

    public void edit(Policy policy) {
        policyRepository.save(policy);
    }

    public void delete(Long id) {
        policyRepository.deleteById(id);
    }
}