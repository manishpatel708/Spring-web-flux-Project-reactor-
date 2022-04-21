package com.ninjatech.webflux.controller;

import com.ninjatech.webflux.dto.EmployeeDto;
import com.ninjatech.webflux.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService EmployeeService;

    @GetMapping
    public Flux<EmployeeDto> getAllEmployee() {
        return EmployeeService.getEmployees();
    }

    @PostMapping
    public Mono<EmployeeDto> saveEmployee(@RequestBody Mono<EmployeeDto> employeeDto) {
        return EmployeeService.saveEmployee(employeeDto);
    }

    @GetMapping("/{id}")
    public Mono<EmployeeDto> findEmployeeById(@PathVariable String id) {
        return EmployeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public Mono<EmployeeDto> updateEmployee(@PathVariable String id, @RequestBody Mono<EmployeeDto> employeeDto) {
        return EmployeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteEmployee(@PathVariable String id) {
        return EmployeeService.deleteEmployee(id);
    }

    @GetMapping("/salary-range")
    public Flux<EmployeeDto> getEmployeeBetweenRange(@RequestParam("min") double min, @RequestParam("max") double max) {
        return EmployeeService.getEmployeeInSalaryRange(min, max);
    }
}
