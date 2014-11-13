package ro.z2h.service;

import ro.z2h.dao.JobDao;
import ro.z2h.domain.Job;
import ro.z2h.utils.DatabaseManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by George on 11/13/2014.
 */
public class JobServiceImpl implements JobService{

    @Override
    public Job findOneJob(String id){
        Connection con = DatabaseManager.getConnection(DatabaseManager.USERNAME, DatabaseManager.PASSWORD);
        DatabaseManager.checkConnection(con);

        JobDao jobDao = new JobDao();
        try {
            return jobDao.getJobById(con, id);
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
    public List<Job> findAllJobs() {
        Connection con = DatabaseManager.getConnection(DatabaseManager.USERNAME, DatabaseManager.PASSWORD);
        DatabaseManager.checkConnection(con);

        JobDao jobDao = new JobDao();
        try {
            return jobDao.getAllJobs(con);
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
