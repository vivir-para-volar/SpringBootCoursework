package com.irinalyamina.InsuranceAgency.modelsForLayout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoLayout {

    private Long id;
    private String photo;
    private LocalDate uploadDate;

}