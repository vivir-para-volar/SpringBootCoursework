package com.irinalyamina.InsuranceAgency.modelsForLayout;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class InsuranceEventForList {

    private Long id;
    private LocalDate incidentDate;
    private int insurancePayment;
    private String description;

    private String insuranceType;
    private String policyholderFullName;
    private String carModel;

    public InsuranceEventForList(InsuranceEvent insuranceEvent, String insuranceType, String policyholderFullName, String carModel) {
        this.id = insuranceEvent.getId();
        this.incidentDate = insuranceEvent.getIncidentDate();
        this.insurancePayment = insuranceEvent.getInsurancePayment();
        this.description = insuranceEvent.getDescription();

        this.insuranceType = insuranceType;
        this.policyholderFullName = policyholderFullName;
        this.carModel = carModel;
    }
}
