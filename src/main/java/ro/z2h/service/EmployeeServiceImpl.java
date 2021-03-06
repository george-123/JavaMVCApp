package ro.z2h.service;

import ro.z2h.dao.EmployeeDao;
import ro.z2h.domain.Employee;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by George on 11/12/2014.
 */
public class EmployeeServiceImpl implements EmployeeService {

    public List<Employee> findAllEmployees(){
        Connection con = DatabaseManager.getConnection(DatabaseManager.USERNAME, DatabaseManager.PASSWORD);
        DatabaseManager.checkConnection(con);

        EmployeeDao employeeDao = new EmployeeDao();
        try {
            return employeeDao.getAllEmployees(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }


    /*
    public List<Employee> findAllEmployees(){

        List<Employee> employeeList = new ArrayList<>();
        Employee emp1 = new Employee();
        emp1.setId(18L);
        emp1.setLastName("lala1");
        emp1.setFirstName("firstName1");
        employeeList.add(emp1);

        Employee emp2 = new Employee();
        emp2.setId(780L);
        emp2.setLastName("lala1");
        emp2.setFirstName("firstName2");
        employeeList.add(emp2);
        return employeeList;
    }
    */

    public Employee findOneEmployee(Long id){
        Connection con = DatabaseManager.getConnection(DatabaseManager.USERNAME, DatabaseManager.PASSWORD);
        DatabaseManager.checkConnection(con);

        EmployeeDao employeeDao = new EmployeeDao();
        try {
            return employeeDao.getEmployeeById(con, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
