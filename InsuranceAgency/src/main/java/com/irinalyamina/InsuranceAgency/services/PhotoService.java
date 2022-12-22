package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }
}