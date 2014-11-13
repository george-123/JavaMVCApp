package ro.z2h.controller;

import ro.z2h.annotation.MyController;
import ro.z2h.annotation.MyRequestMethod;
import ro.z2h.domain.Job;
import ro.z2h.service.JobServiceImpl;

import java.util.List;

/**
 * Created by George on 11/13/2014.
 */

@MyController(urlPath = "/job")
public class JobController {


    @MyRequestMethod(urlPath = "/all")
    public List<Job> getAllJobs() {
        return (new JobServiceImpl().findAllJobs());
    }

    @MyRequestMethod(urlPath = "/one")
    public Job getOneJob(String idJob) {
        return (new JobServiceImpl().findOneJob(idJob));
    }
}
