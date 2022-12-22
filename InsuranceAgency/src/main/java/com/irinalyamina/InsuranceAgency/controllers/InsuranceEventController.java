package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.modelsForLayout.InsuranceEventForList;
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
        List<InsuranceEventForList> list = new ArrayList<>();

        for (var i = 0; i < listInsuranceEvents.size(); i++) {
            list.add(new InsuranceEventForList(
                    listInsuranceEvents.get(i),
                    listInsuranceEvents.get(i).getPolicy().getInsuranceType(),
                    listInsuranceEvents.get(i).getPolicy().getPolicyholder().getFullName(),
                    listInsuranceEvents.get(i).getPolicy().getCar().getModel()
            ));
        }

        model.addAttribute("insuranceEvents", list);
        return "insuranceEvent/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        InsuranceEvent insuranceEvent = insuranceEventService.getById(id);
        model.addAttribute("insuranceEvent", insuranceEvent);
        model.addAttribute("policy", insuranceEvent.getPolicy());
        model.addAttribute("policyholder", insuranceEvent.getPolicy().getPolicyholder());
        model.addAttribute("car", insuranceEvent.getPolicy().getCar());
        model.addAttribute("employee", insuranceEvent.getPolicy().getEmployee());
        model.addAttribute("personsAllowedToDrive", insuranceEvent.getPolicy().getPersonsAllowedToDrive());
        return "insuranceEvent/details";
    }
}