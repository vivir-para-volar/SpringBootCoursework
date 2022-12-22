package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Car;
import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.CarService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}