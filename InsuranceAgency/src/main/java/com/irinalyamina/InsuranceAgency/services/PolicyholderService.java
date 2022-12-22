package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.repositories.PolicyholderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyholderService {

    private final PolicyholderRepository policyholderRepository;

    public PolicyholderService(PolicyholderRepository policyholderRepository) {
        this.policyholderRepository = policyholderRepository;
    }

    public List<Policyholder> list(){
        return policyholderRepository.findAll();
    }

    public Policyholder getById(Long id) {
        return policyholderRepository.findById(id).get();
    }
}