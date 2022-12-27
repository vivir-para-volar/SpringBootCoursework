package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.InteractionPhoto;
import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Photo;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PhotoLayout;
import com.irinalyamina.InsuranceAgency.services.CarService;
import com.irinalyamina.InsuranceAgency.services.PhotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/car/create")
public class CreateCarController {

    private final CarService carService;
    private final PhotoService photoService;

    public CreateCarController(CarService carService, PhotoService photoService) {
        this.carService = carService;
        this.photoService = photoService;
    }

    @GetMapping()
    public String createGet(Model model) {
        model.addAttribute("car", new Car());
        return "car/create/chooseInfo";
    }

    @PostMapping("/chooseInfo")
    public String createChooseInfo(Model model, @ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        checkForUniqueness(car, bindingResult);
        if (bindingResult.hasErrors()) {
            return "car/create/chooseInfo";
        }

        car = carService.create(car);
        InteractionPhoto.createDir(car.getId());

        model.addAttribute("carId", car.getId());
        return "car/create/addPhotos";
    }

    @PostMapping("/upload/{carId}")
    public String uploadImage(Model model, @PathVariable("carId") Long carId, @RequestParam("image") MultipartFile file) {
        String fileName;
        try {
            fileName = InteractionPhoto.uploadImage(file, carId);
        } catch (Exception exp) {
            return "car/create/addPhotos";
        }

        var photo = new Photo();
        photo.setPath(fileName);
        photo.setUploadDate(LocalDate.now());
        photo.setCar(carService.getById(carId));
        photoService.create(photo);

        try {
            List<PhotoLayout> photos = InteractionPhoto.getPhotos(carService.getById(carId));
            model.addAttribute("photos", photos);
        } catch (Exception exp) {
            model.addAttribute("photos", null);
        }
        model.addAttribute("carId", carId);
        return "car/create/addPhotos";
    }

    private void checkForUniqueness(Car car, BindingResult bindingResult) {
        if (checkVin(car)) {
            bindingResult.addError(new FieldError(
                    "car", "vin",
                    car.getVin(),
                    false, null, null,
                    "Данный VIN номер уже используется")
            );
        }
    }

    private boolean checkVin(Car car) {
        if (car.getId() == null) {
            return carService.checkVin(car.getVin());
        } else {
            return carService.checkVinExceptId(car.getId(), car.getVin());
        }
    }
}