package com.motondon.dagger2_demoapp.presentation.presenter.task;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.TaskException;
import com.motondon.dagger2_demoapp.domain.interactor.CreateTaskInteractor;
import com.motondon.dagger2_demoapp.model.task.TaskModel;
import com.motondon.dagger2_demoapp.model.user.UserModel;

import javax.inject.Inject;

public class TaskCreatePresenterImpl implements TaskCreatePresenter {
    private static final String TAG = TaskCreatePresenterImpl.class.getSimpleName();

    @Inject
    TaskRepositoryImpl taskRepository;

    @Inject
    UserModel userModel;

    // We need to explicitly add this constructor in order to add @Inject dagger annotation, otherwise Dagger will not know how to construct it.
    @Inject
    public TaskCreatePresenterImpl() {
    }

    @Override
    public void createTask(TaskModel newTask, TaskCreatePresenter.View view) {
        Log.d(TAG, "createTask() - newTask: " + newTask.getName());

        CreateTaskInteractor createTaskInteractor = new CreateTaskInteractor(taskRepository);

        try {
            createTaskInteractor.execute(userModel.getName(), newTask);
            Log.d(TAG, "createTask() - Task: " + newTask.getName() + " created successfully");

            view.taskCreatedSuccessfully(newTask);
           
        } catch (TaskException e) {
            Log.e(TAG, "createTask() - Failure when trying to create task: " + newTask.getName() + ": " + e.getMessage());
            view.requestFailure(e.getMessage());
        }
    }
}
