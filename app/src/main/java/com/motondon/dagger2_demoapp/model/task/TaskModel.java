package com.motondon.dagger2_demoapp.model.task;

import java.io.Serializable;

public class TaskModel implements Serializable {

    private String taskName;
    private String taskDetails;

    public TaskModel(String taskName, String taskDetails) {
        this.taskName = taskName;
        this.taskDetails = taskDetails;
    }

    public String getName() {
        return taskName;
    }

    public void setName(String taskname) {
        this.taskName = taskname;
    }

    public String getDetails() {
        return taskDetails;
    }

    public void setDetails(String taskdetails) {
        this.taskDetails = taskdetails;
    }
}
