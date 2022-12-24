package com.irinalyamina.InsuranceAgency.repositories;

import com.irinalyamina.InsuranceAgency.models.PersonAllowedToDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonAllowedToDriveRepository extends JpaRepository<PersonAllowedToDrive, Long> {

    boolean existsByDrivingLicence(String drivingLicence);

    @Query(
            value = "SELECT CASE WHEN EXISTS (SELECT * FROM person_allowed_to_drive WHERE driving_licence = :drivingLicence AND id <> :id) THEN 'true' ELSE 'false' END",
            nativeQuery = true)
    boolean existsByDrivingLicenceExceptId(Long id, String drivingLicence);

}