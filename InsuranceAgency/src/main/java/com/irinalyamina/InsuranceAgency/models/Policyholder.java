package com.irinalyamina.InsuranceAgency.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Policyholder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 64, nullable = false)
    @NotNull(message = "Введите ФИО")
    @Size(max = 64, message = "ФИО не должено превышать 64 символа")
    private String fullName;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, fallbackPatterns = { "dd.MM.yyyy" })
    @NotNull(message = "Введите Дату рождения")
    private LocalDate birthday;

    @Column(length = 12, unique = true, nullable = false)
    @Pattern(regexp = "\\+7|8\\d{10}", message = "Некорректный ввод номера телефона")
    @NotNull(message = "Введите Номер телефона")
    private String telephone;

    @Column(length = 32, unique = true)
    @Email(message = "Некорректный ввод для Email")
    @Size(max = 32, message = "Email не должен превышать 32 символа")
    @Nullable
    private String email;

    @Column(length = 10, unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]*$", message = "Паспорт может включать только цифры")
    @Size(min = 10, max = 10, message = "Пасспорт должен содержать 10 цифр")
    @NotNull(message = "Введите Номер телефона")
    private String passport;

    @OneToMany(mappedBy = "policyholder")
    @JsonManagedReference
    private List<Policy> policies;

}