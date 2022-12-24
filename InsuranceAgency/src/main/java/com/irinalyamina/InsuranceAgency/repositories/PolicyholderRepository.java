package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.Policyholder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PolicyholderRepository extends JpaRepository<Policyholder, Long> {

    boolean existsByTelephone(String telephone);

    boolean existsByEmail(String email);

    boolean existsByPassport(String passport);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM policyholder WHERE telephone = :telephone AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByTelephoneExceptId(Long id, String telephone);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM policyholder WHERE email = :email AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByEmailExceptId(Long id, String email);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM policyholder WHERE passport = :passport AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByPassportExceptId(Long id, String passport);

}