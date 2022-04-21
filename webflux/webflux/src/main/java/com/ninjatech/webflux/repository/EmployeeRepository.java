package com.ninjatech.webflux.repository;

import com.ninjatech.webflux.model.Employee;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String> {
    Flux<Employee> findBySalaryBetween(Range<Double> salary);
}
