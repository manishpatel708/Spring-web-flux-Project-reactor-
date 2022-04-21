package com.ninjatech.webflux.service;

import com.ninjatech.webflux.dto.EmployeeDto;
import com.ninjatech.webflux.repository.EmployeeRepository;
import com.ninjatech.webflux.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Flux<EmployeeDto> getEmployees() {
        return employeeRepository.findAll().map(AppUtils::entityToDto);
    }

    public Mono<EmployeeDto> getEmployeeById(String id) {
        return employeeRepository.findById(id).map(AppUtils::entityToDto);
    }

    public Flux<EmployeeDto> getEmployeeInSalaryRange(double min, double max) {
        return employeeRepository.findBySalaryBetween(Range.closed(min, max)).map(AppUtils::entityToDto);
    }

    public Mono<EmployeeDto> saveEmployee(Mono<EmployeeDto> employeeDto) {
        return employeeDto.map(AppUtils::dtoToEntity)
                .flatMap(employeeRepository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<EmployeeDto> updateEmployee(String id, Mono<EmployeeDto> employeeDtoMono) {
        return employeeRepository.findById(id)
                .flatMap(e -> employeeDtoMono.map(AppUtils::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .flatMap(employeeRepository::insert)
                .map(AppUtils::entityToDto);
    }

    public Mono<Void> deleteEmployee(String id) {
        return employeeRepository.deleteById(id);
    }
}
