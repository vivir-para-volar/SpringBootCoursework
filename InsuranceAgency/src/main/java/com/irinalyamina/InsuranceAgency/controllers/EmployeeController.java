package com.irinalyamina.InsuranceAgency.controllers;

import com.irinalyamina.InsuranceAgency.Parse;
import com.irinalyamina.InsuranceAgency.models.Employee;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;
import com.irinalyamina.InsuranceAgency.services.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("employees", employeeService.list());
        return "employee/list";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long id) {
        Employee employee = employeeService.getById(id);
        List<PolicyForList> list = Parse.listPolicyToListPolicyForList(employee.getPolicies());

        model.addAttribute("employee", employee);
        model.addAttribute("policies", list);
        return "employee/details";
    }
}