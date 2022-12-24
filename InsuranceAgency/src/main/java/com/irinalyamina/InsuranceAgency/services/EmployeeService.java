package com.irinalyamina.InsuranceAgency.services;

import com.irinalyamina.InsuranceAgency.models.Employee;
import com.irinalyamina.InsuranceAgency.repositories.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id).get();
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void edit(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public boolean checkTelephone(String telephone) {
        return employeeRepository.existsByTelephone(telephone);
    }

    public boolean checkEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public boolean checkPassport(String passport) {
        return employeeRepository.existsByPassport(passport);
    }

    public boolean checkTelephoneExceptId(Long id, String telephone) {
        return employeeRepository.existsByTelephoneExceptId(id, telephone);
    }

    public boolean checkEmailExceptId(Long id, String email) {
        return employeeRepository.existsByEmailExceptId(id, email);
    }

    public boolean checkPassportExceptId(Long id, String passport) {
        return employeeRepository.existsByPassportExceptId(id, passport);
    }
}