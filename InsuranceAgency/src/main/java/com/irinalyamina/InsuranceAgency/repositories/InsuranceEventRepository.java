package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceEventRepository extends JpaRepository<InsuranceEvent, Long> {

}