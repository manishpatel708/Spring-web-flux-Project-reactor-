package com.ninjatech.webflux.controller;

import com.ninjatech.webflux.dto.EmployeeDto;
import com.ninjatech.webflux.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void addEmployeeTest() {
        Mono<EmployeeDto> employeeDtoMono = Mono.just(new EmployeeDto(1, "Manish", "backend", 75000, "9737834722"));
        when(employeeService.saveEmployee(employeeDtoMono)).thenReturn(employeeDtoMono);

        webTestClient.post().uri("/api/employee")
                .body(Mono.just(employeeDtoMono), EmployeeDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void getEmployeeListTest() {
        Flux<EmployeeDto> employeeDtoFlux = Flux.just(
                new EmployeeDto(1, "Manish", "backend", 75000, "9737834722"),
                new EmployeeDto(2, "Manish Patel", "full stack", 175000, "9737834722"));

        when(employeeService.getEmployees()).thenReturn(employeeDtoFlux);

        Flux<EmployeeDto> responseBody = webTestClient.get().uri("/api/employee")
                .exchange()
                .expectStatus().isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto(1, "Manish", "backend", 75000, "9737834722"))
                .expectNext(new EmployeeDto(2, "Manish Patel", "full stack", 175000, "9737834722"))
                .verifyComplete();
    }

    @Test
    public void getEmployeeTest() {
        Mono<EmployeeDto> employeeDtoMono = Mono.just(new EmployeeDto(1, "Manish", "backend", 75000, "9737834722"));

        when(employeeService.getEmployeeById("1")).thenReturn(employeeDtoMono);

        Flux<EmployeeDto> responseBody = webTestClient.get().uri("/api/employee/1")
                .exchange()
                .expectStatus().isOk()
                .returnResult(EmployeeDto.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(new EmployeeDto(1, "Manish", "backend", 75000, "9737834722"))
                .verifyComplete();
    }

    @Test
    public void updateEmployeeTest() {
        Mono<EmployeeDto> employeeDtoMono = Mono.just(new EmployeeDto(1, "Manish", "backend", 75000, "9737834722"));
        when(employeeService.updateEmployee("1", employeeDtoMono).thenReturn(employeeDtoMono));

        webTestClient.put().uri("/api/employee/1")
                .body(Mono.just(employeeDtoMono), EmployeeDto.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
    
    @Test
    public void deleteEmployeeTest() {
        given(employeeService.deleteEmployee(any())).willReturn(Mono.empty());
        webTestClient.delete().uri("/products/delete/102")
                .exchange()
                .expectStatus().isOk();//200
    }
}