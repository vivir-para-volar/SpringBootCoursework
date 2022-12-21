package com.irinalyamina.InsuranceAgency.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    @NotNull(message = "Введите Марку, модель")
    @Size(max = 50, message = "Модель не должна превышать 50 символов")
    private String model;

    @Column(length = 17, unique = true, nullable = false)
    @NotNull(message = "Введите VIN номер")
    @Size(min = 17, max = 17, message = "VIN номер должен содержать 17 символов")
    private String vin;

    @Column(length = 25, nullable = false)
    @NotNull(message = "Введите Регистрационный знак")
    @Size(max = 25, message = "Регистрационный знак не должен превышать 25 символов")
    private String registrationPlate;

    @Column(length = 10, nullable = false)
    @NotNull(message = "Введите Паспорт ТС")
    @Size(min = 10, max = 10, message = "Паспорт ТС должен содержать 10 символов")
    private String vehiclePassport;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Photo> photos;

    @OneToMany(mappedBy = "car")
    @JsonManagedReference
    private List<Policy> policies;

}