package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.Photo;
import com.irinalyamina.InsuranceAgency.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    public Photo getById(Long id) {
        return photoRepository.findById(id).get();
    }

    public Photo create(Photo photo) {
        return photoRepository.save(photo);
    }

    public void delete(Long id) {
        photoRepository.deleteById(id);
    }
}