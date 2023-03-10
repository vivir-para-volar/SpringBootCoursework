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

    public List<Policyholder> list() {
        return policyholderRepository.findAll();
    }

    public Policyholder getById(Long id) {
        return policyholderRepository.findById(id).get();
    }

    public Policyholder getByEmail(String email) {
        return policyholderRepository.findByEmail(email).get();
    }

    public Policyholder create(Policyholder policyholder) {
        return policyholderRepository.save(policyholder);
    }

    public void edit(Policyholder policyholder) {
        policyholderRepository.save(policyholder);
    }

    public void delete(Long id) {
        policyholderRepository.deleteById(id);
    }


    public boolean checkTelephone(String telephone) {
        return policyholderRepository.existsByTelephone(telephone);
    }

    public boolean checkEmail(String email) {
        return policyholderRepository.existsByEmail(email);
    }

    public boolean checkPassport(String passport) {
        return policyholderRepository.existsByPassport(passport);
    }

    public boolean checkTelephoneExceptId(Long id, String telephone) {
        return policyholderRepository.existsByTelephoneExceptId(id, telephone);
    }

    public boolean checkEmailExceptId(Long id, String email) {
        return policyholderRepository.existsByEmailExceptId(id, email);
    }

    public boolean checkPassportExceptId(Long id, String passport) {
        return policyholderRepository.existsByPassportExceptId(id, passport);
    }
}