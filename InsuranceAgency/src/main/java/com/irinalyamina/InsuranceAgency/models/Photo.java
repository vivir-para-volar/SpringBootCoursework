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
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    @NotNull(message = "Введите Путь")
    @Size(max = 100, message = "Путь не должен превышать 100 символов")
    private String path;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = { "dd.MM.yyyy" })
    @NotNull(message = "Введите Дату загрузки")
    private LocalDate uploadDate;

    @ManyToOne
    @JsonBackReference
    private Car car;

}