package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Employee;
import ro.z2h.service.EmployeeService;
import ro.z2h.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 11/11/2014.
 */
@MyController(urlPath = "/employee")
public class EmployeeController {

    @MyRequestMethod(urlPath = "/all")
    public List<Employee> getAllEmployees(){

        return (new EmployeeServiceImpl().findAllEmployees());

        /*
        List<Employee> employees = new ArrayList<Employee>();

        Employee employee = new Employee();
        employee.setId(1L);
        employee.setLastName("George");

        Employee employee2 = new Employee();
        employee2.setId(2L);
        employee2.setLastName("George2");

        employees.add(employee);
        employees.add(employee2);

        return employees;
        */
    }

    @MyRequestMethod(urlPath = "/one")
    public Employee getOneEmployee(String idEmployee){
        /*
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setLastName("George");
        return employee;
        */
        return (new EmployeeServiceImpl().findOneEmployee(Long.parseLong(idEmployee)));
    }

    @MyRequestMethod(urlPath = "/two")
    public String lala(String a, String b){
        return (a + " == " + b);
    }
}
