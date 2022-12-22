package com.irinalyamina.InsuranceAgency.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    @NotNull(message = "Введите Вид страхования")
    @Size(min = 5, max = 5, message = "Вид страхования должен содержать 5 символов")
    private String insuranceType;

    @Column(nullable = false)
    @NotNull(message = "Введите Страховую сумму")
    private int insurancePremium;

    @Column(nullable = false)
    @NotNull(message = "Введите Страховую премию")
    private int insuranceAmount;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите Дату заключения")
    private LocalDate dateOfConclusion;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите Дату окончания действия")
    private LocalDate expirationDate;

    @ManyToOne
    @JsonBackReference
    private Policyholder policyholder;

    @ManyToOne
    @JsonBackReference
    private Car car;

    @ManyToOne
    @JsonBackReference
    private Employee employee;

    @OneToMany(mappedBy = "policy")
    @JsonManagedReference
    private List<InsuranceEvent> insuranceEvents;

    @ManyToMany()
    @JsonManagedReference
    private List<PersonAllowedToDrive> personsAllowedToDrive;

}