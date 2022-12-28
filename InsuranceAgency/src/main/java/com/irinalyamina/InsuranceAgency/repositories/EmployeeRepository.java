package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByTelephone(String telephone);

    boolean existsByEmail(String email);

    boolean existsByPassport(String passport);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM employee WHERE telephone = :telephone AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByTelephoneExceptId(Long id, String telephone);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM employee WHERE email = :email AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByEmailExceptId(Long id, String email);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM employee WHERE passport = :passport AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByPassportExceptId(Long id, String passport);

}