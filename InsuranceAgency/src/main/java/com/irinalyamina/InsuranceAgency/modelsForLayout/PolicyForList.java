package com.irinalyamina.InsuranceAgency.modelsForLayout;

import com.irinalyamina.InsuranceAgency.models.Policy;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PolicyForList {

    private Long id;
    private String insuranceType;
    private int insurancePremium;
    private int insuranceAmount;
    private LocalDate dateOfConclusion;
    private LocalDate expirationDate;

    private String policyholderFullName;
    private String carModel;

    public PolicyForList(Policy policy, String policyholderFullName, String carModel) {
        this.id = policy.getId();
        this.insuranceType = policy.getInsuranceType();
        this.insurancePremium = policy.getInsurancePremium();
        this.insuranceAmount = policy.getInsuranceAmount();
        this.dateOfConclusion = policy.getDateOfConclusion();
        this.expirationDate = policy.getExpirationDate();

        this.policyholderFullName = policyholderFullName;
        this.carModel = carModel;
    }
}