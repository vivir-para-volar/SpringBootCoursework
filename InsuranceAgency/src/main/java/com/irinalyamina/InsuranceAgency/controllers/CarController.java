package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.models.Policyholder;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
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
        List<PolicyForList> list = Parse.listPolicyToListPolicyForList(car.getPolicies());

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
        if (carService.checkVin(car.getVin())) {
            bindingResult.addError(new FieldError(
                    "car", "vin",
                    car.getVin(),
                    false, null, null,
                    "Данный VIN номер уже используется")
            );
        }
        if (bindingResult.hasErrors()) {
            return "car/create";
        }

        car = carService.create(car);
        return "redirect:/car/details/" + car.getId();
    }
}