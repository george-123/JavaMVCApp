package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;

/**
 * Created by George on 11/11/2014.
 */
@MyController(urlPath = "/department")
public class DepartmentController {

    @MyRequestMethod(urlPath = "/all")
    public String getAllDepartment(){
        return "allDepartments";
    }

    @MyRequestMethod(urlPath = "/one")
    public String getOneDepartment(){
        return "randomOneDepartment";
    }
}
