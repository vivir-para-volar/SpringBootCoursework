package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface InsuranceEventRepository extends JpaRepository<InsuranceEvent, Long> {

    @Query(
            value = "SELECT MAX(incident_date) FROM insurance_event WHERE policy_id = :policyId",
            nativeQuery = true)
    LocalDate searchMaxIncidentDateByPolicyId(Long policyId);

}