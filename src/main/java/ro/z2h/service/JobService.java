package ro.z2h.service;

import ro.z2h.domain.Job;

import java.util.List;

/**
 * Created by George on 11/13/2014.
 */
public interface JobService {

    List<Job> findAllJobs();
    Job findOneJob(String id);
}
