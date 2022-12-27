package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.InteractionPhoto;
import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Photo;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PhotoLayout;
import com.irinalyamina.InsuranceAgency.services.CarService;
import com.irinalyamina.InsuranceAgency.services.PhotoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;
    private final PhotoService photoService;

    public CarController(CarService carService, PhotoService photoService) {
        this.carService = carService;
        this.photoService = photoService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("cars", carService.list());
        return "car/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Car car = carService.getById(id);
        List<Policy> list = car.getPolicies();

        model.addAttribute("car", car);
        try {
            List<PhotoLayout> photos = InteractionPhoto.getPhotos(carService.getById(id));
            model.addAttribute("photos", photos);
        } catch (Exception exp) {
            model.addAttribute("photos", null);
        }
        model.addAttribute("policies", list);
        return "car/details";
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("car", carService.getById(id));
        try {
            List<PhotoLayout> photos = InteractionPhoto.getPhotos(carService.getById(id));
            model.addAttribute("photos", photos);
        } catch (Exception exp) {
            model.addAttribute("photos", null);
        }
        return "car/edit/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "car/edit/edit";
        }

        carService.edit(car);
        return "redirect:/car/details/" + car.getId();
    }

    @GetMapping("/deletePhoto/{photoId}")
    public String deletePhotoGet(Model model, @PathVariable("photoId") Long photoId) {
        Photo photo = photoService.getById(photoId);
        model.addAttribute("photo", photo);
        try {
            PhotoLayout photoLayout = InteractionPhoto.getPhoto(photo);
            model.addAttribute("image", photoLayout.getPhoto());
        } catch (Exception exp) {
            model.addAttribute("image", null);
        }
        return "car/edit/deletePhoto";
    }

    @PostMapping("/deletePhoto")
    public String deletePhotoPost(@ModelAttribute("photo") Photo photo) {
        photoService.delete(photo.getId());
        InteractionPhoto.deletePhoto(photo);
        return "redirect:/car/details/" + photo.getCar().getId();
    }

    @GetMapping("/addPhoto/{carId}")
    public String addPhotoGet(Model model, @PathVariable("carId") Long carId) {
        model.addAttribute("carId", carId);
        return "car/edit/addPhoto";
    }

    @PostMapping("/addPhoto/{carId}")
    public String addPhotoPost(Model model, @PathVariable("carId") Long carId, @RequestParam("image") MultipartFile file) {
        String fileName;
        try {
            fileName = InteractionPhoto.uploadImage(file, carId);
        } catch (Exception exp) {
            return "redirect:/car/details/" + carId;
        }

        var photo = new Photo();
        photo.setPath(fileName);
        photo.setUploadDate(LocalDate.now());
        photo.setCar(carService.getById(carId));
        photoService.create(photo);

        return "redirect:/car/details/" + carId;
    }

    @GetMapping("/delete/{id}")
    public String deleteGet(Model model, @PathVariable("id") Long id) {
        Car car = carService.getById(id);
        model.addAttribute("car", car);
        model.addAttribute("hasPolicies", car.getPolicies().size() != 0);
        return "car/delete";
    }

    @PostMapping("/delete")
    public String deletePost(Model model, @ModelAttribute("car") Car car) {
        Car carDelete = carService.getById(car.getId());
        if (carDelete.getPolicies().size() != 0) {
            model.addAttribute("car", carDelete);
            model.addAttribute("hasPolicies", true);
            return "car/delete";
        }

        carService.delete(carDelete.getId());
        return "redirect:/car/list";
    }
}