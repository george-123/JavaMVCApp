package ro.z2h.service;

import ro.z2h.domain.Employee;

import java.util.List;

/**
* Created by George on 11/12/2014.
        */
public interface EmployeeService {
    List<Employee> findAllEmployees();
    Employee findOneEmployee(Long id);
}
