package com.motondon.dagger2_demoapp.presentation.presenter.task;

import com.motondon.dagger2_demoapp.model.task.TaskModel;

public interface TaskCreatePresenter {
	
	interface View {
        void taskCreatedSuccessfully(TaskModel task);
        void requestFailure(String errorMessage);
	}
	
    void createTask(TaskModel newTask, View view);
}
