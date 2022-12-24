package com.irinalyamina.InsuranceAgency.extendedModels;

import com.irinalyamina.InsuranceAgency.models.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class InsuranceEventExtended {

    private Long id;
    private LocalDate incidentDate;
    private int insurancePayment;
    private String description;

    private Policy policy;
    private Policyholder policyholder;
    private Car car;
    private Employee employee;

    public InsuranceEventExtended(InsuranceEvent insuranceEvent) {
        this.id = insuranceEvent.getId();
        this.incidentDate = insuranceEvent.getIncidentDate();
        this.insurancePayment = insuranceEvent.getInsurancePayment();
        this.description = insuranceEvent.getDescription();

        this.policy = insuranceEvent.getPolicy();
        this.policyholder = insuranceEvent.getPolicy().getPolicyholder();
        this.car = insuranceEvent.getPolicy().getCar();
        this.employee = insuranceEvent.getPolicy().getEmployee();
    }
}