package com.irinalyamina.InsuranceAgency.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class InsuranceEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = { "dd.MM.yyyy" })
    @NotNull(message = "Введите Дату")
    private LocalDate incidentDate;

    @Column(nullable = false)
    @NotNull(message = "Введите Страховую выплату")
    private int insurancePayment;

    @Column(length = 1000, nullable = false)
    @NotNull(message = "Введите Описание")
    @Size(max = 1000, message = "Описание не должно превышать 1000 символов")
    private String description;

    @ManyToOne
    @JsonBackReference
    private Policy policy;

}