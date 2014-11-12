package ro.z2h.service;

import ro.z2h.dao.DepartmentDao;
import ro.z2h.domain.Department;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by George on 11/12/2014.
 */
public class DepartmentServiceImpl implements DepartmentService {
    @Override
    public List<Department> findAllDepartments() {
        Connection con = DatabaseManager.getConnection(DatabaseManager.USERNAME, DatabaseManager.PASSWORD);
        DatabaseManager.checkConnection(con);

        DepartmentDao departmentDao = new DepartmentDao();
        try {
            return departmentDao.getAllDepartments(con);
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

    @Override
    public Department findOneDepartment(Long id) {
        Connection con = DatabaseManager.getConnection(DatabaseManager.USERNAME, DatabaseManager.PASSWORD);
        DatabaseManager.checkConnection(con);

        DepartmentDao departmentDao = new DepartmentDao();
        try {
            return departmentDao.getDepartmentById(con, id);
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
