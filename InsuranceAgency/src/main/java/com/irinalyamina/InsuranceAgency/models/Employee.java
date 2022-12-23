package com.irinalyamina.InsuranceAgency.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    @NotEmpty(message = "Введите ФИО")
    @Size(max = 64, message = "ФИО не должно превышать 64 символа")
    private String fullName;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = {"dd.MM.yyyy"})
    @NotNull(message = "Введите Дату рождения")
    private LocalDate birthday;

    @Column(length = 15, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]*$", message = "Номер телефона может включать только цифры")
    @Size(max = 15, message = "Номер телефона не должен превышать 15 цифр")
    @NotEmpty(message = "Введите Номер телефона")
    private String telephone;

    @Column(length = 32, unique = true, nullable = false)
    @Email(message = "Некорректный ввод для Email")
    @Size(max = 32, message = "Email не должен превышать 32 символа")
    @NotEmpty(message = "Введите Email")
    private String email;

    @Column(length = 10, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]*$", message = "Паспорт может включать только цифры")
    @Size(min = 10, max = 10, message = "Паспорт должен содержать 10 цифр")
    private String passport;

    @OneToMany(mappedBy = "employee")
    @JsonManagedReference
    private List<Policy> policies;

}