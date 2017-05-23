package com.motondon.dagger2_demoapp.domain.interactor;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.TaskException;
import com.motondon.dagger2_demoapp.model.task.TaskModel;

public class CreateTaskInteractor {
    private static final String TAG = CreateTaskInteractor.class.getSimpleName();

    private TaskRepositoryImpl taskRepository;

    public CreateTaskInteractor(TaskRepositoryImpl taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(String userName, TaskModel newTask) throws TaskException {
        Log.d(TAG, "execute() - userName: " + userName + " - newTask: " + newTask.getName());

    	if (taskRepository.containTask(userName, newTask)) {
            throw new TaskException("Task " + newTask.getName() + " already exists");
        }

        try {
        	
            taskRepository.createTask(userName, newTask);
            Log.d(TAG, "execute() - Task: " + newTask.getName() + " created successfully");

        } catch (TaskException e) {
            Log.e(TAG, "execute() - Failure when creating task " + newTask.getName()+ ": " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Log.e(TAG, "execute() - Failure when creating task " + newTask.getName()+ ": " + e.getMessage());
            throw new TaskException("Failure when creating task " + newTask.getName());
        }
    }
}
