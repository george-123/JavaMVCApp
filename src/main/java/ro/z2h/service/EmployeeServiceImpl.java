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
