package com.irinalyamina.InsuranceAgency.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PersonAllowedToDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    @NotEmpty(message = "Введите ФИО")
    @Size(max = 64, message = "ФИО не должно превышать 64 символа")
    private String fullName;

    @Column(length = 10, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]*$", message = "Водительское удостоверение может включать только цифры")
    @Size(min = 10, max = 10, message = "Водительское удостоверение должно содержать 10 цифр")
    private String drivingLicence;

    @ManyToMany(mappedBy = "personsAllowedToDrive")
    @JsonManagedReference
    private List<Policy> policies;

}