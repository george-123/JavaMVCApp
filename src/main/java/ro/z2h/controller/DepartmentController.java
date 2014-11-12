package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Department;
import ro.z2h.service.DepartmentServiceImpl;
import ro.z2h.service.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 11/11/2014.
 */
@MyController(urlPath = "/department")
public class DepartmentController {

    @MyRequestMethod(urlPath = "/all")
    public List<Department> getAllDepartment(){
        return (new DepartmentServiceImpl().findAllDepartments());
        /*
        List<Department> departmentList = new ArrayList<Department>();

        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("myDepartment");
        departmentList.add(department);


        Department department2 = new Department();
        department2.setId(2L);
        department2.setDepartmentName("myDepartment2");
        departmentList.add(department2);

        return departmentList;
        */
    }

    @MyRequestMethod(urlPath = "/one")
    public Department getOneDepartment(String id){
        return (new DepartmentServiceImpl().findOneDepartment(Long.parseLong(id)));
        //          return (new EmployeeServiceImpl().findOneEmployee(Long.parseLong(id)));
        /*
        Department department = new Department();
        department.setId(1L);
        department.setDepartmentName("myDepartment");
        return department;
        */
    }
}
