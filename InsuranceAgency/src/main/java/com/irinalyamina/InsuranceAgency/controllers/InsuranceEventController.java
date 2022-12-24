package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.extendedModels.InsuranceEventExtended;
import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.services.InsuranceEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/insuranceEvent")
public class InsuranceEventController {

    private final InsuranceEventService insuranceEventService;

    public InsuranceEventController(InsuranceEventService insuranceEventService) {
        this.insuranceEventService = insuranceEventService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<InsuranceEvent> listInsuranceEvents = insuranceEventService.list();
        List<InsuranceEventExtended> list = new ArrayList<>();
        for (var item: listInsuranceEvents) {
            list.add(new InsuranceEventExtended(item));
        }

        model.addAttribute("insuranceEvents", list);
        return "insuranceEvent/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        InsuranceEvent insuranceEvent = insuranceEventService.getById(id);
        model.addAttribute("insuranceEvent", insuranceEvent);
        model.addAttribute("policy", insuranceEvent.getPolicy());
        return "insuranceEvent/details";
    }
}