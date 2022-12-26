package com.irinalyamina.InsuranceAgency.modelsForLayout;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class Report {

    @NotEmpty(message = "Введите Вид страхования")
    private String insuranceType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите Дату начала")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите Дату окончания")
    private LocalDate endDate;

}