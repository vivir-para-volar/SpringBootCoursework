package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.Policyholder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyholderRepository extends JpaRepository<Policyholder, Long> {

}