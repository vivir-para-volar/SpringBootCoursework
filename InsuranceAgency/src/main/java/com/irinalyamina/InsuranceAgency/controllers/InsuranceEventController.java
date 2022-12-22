package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.models.InsuranceEvent;
import com.irinalyamina.InsuranceAgency.modelsForLayout.InsuranceEventForList;
import com.irinalyamina.InsuranceAgency.services.InsuranceEventService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
}