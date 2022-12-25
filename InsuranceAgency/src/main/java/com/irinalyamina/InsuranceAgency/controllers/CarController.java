package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.services.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
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
        model.addAttribute("policies", list);
        return "car/details";
    }

    @GetMapping("/create")
    public String createGet(Model model) {
        model.addAttribute("car", new Car());
        return "car/create";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        checkForUniqueness(car, bindingResult);
        if (bindingResult.hasErrors()) {
            return "car/create";
        }

        car = carService.create(car);
        return "redirect:/car/details/" + car.getId();
    }

    @GetMapping("/edit/{id}")
    public String editGet(Model model, @PathVariable("id") Long id) {
        model.addAttribute("car", carService.getById(id));
        return "car/edit";
    }

    @PostMapping("/edit")
    public String editPost(@ModelAttribute("car") @Valid Car car, BindingResult bindingResult) {
        checkForUniqueness(car, bindingResult);
        if (bindingResult.hasErrors()) {
            return "car/edit";
        }

        carService.edit(car);
        return "redirect:/car/details/" + car.getId();
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