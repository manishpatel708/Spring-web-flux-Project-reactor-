package com.ninjatech.webflux.utils;

import com.ninjatech.webflux.dto.EmployeeDto;
import com.ninjatech.webflux.model.Employee;
import org.springframework.beans.BeanUtils;


public class AppUtils {

    public static EmployeeDto entityToDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        BeanUtils.copyProperties(employee, employeeDto);
        return employeeDto;
    }

    public static Employee dtoToEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDto, employee);
        return employee;
    }
}
