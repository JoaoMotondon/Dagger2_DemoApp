package com.motondon.dagger2_demoapp.domain.interactor;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.TaskException;
import com.motondon.dagger2_demoapp.model.task.TaskModel;

public class DeleteTaskInteractor {
    private static final String TAG = DeleteTaskInteractor.class.getSimpleName();

    private TaskRepositoryImpl taskRepository;
    
    public DeleteTaskInteractor(TaskRepositoryImpl taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(String userName, TaskModel task) throws TaskException {
        Log.d(TAG, "execute() - userName: " + userName + " - task: " + task.getName());

        if (!taskRepository.containTask(userName, task)) {
            throw new TaskException("Task " + task.getName() + " does not exist");
        }

        try {
        	taskRepository.deleteTask(userName, task);
            Log.d(TAG, "execute() - Task: " + task.getName() + " deleted successfully");

        } catch (TaskException e) {
            Log.e(TAG, "execute() - Failure when deleting task " + task.getName()+ ": " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Log.e(TAG, "execute() - Failure when deleting task " + task.getName()+ ": " + e.getMessage());
            throw new TaskException("Failure when deleting task " + task.getName());
        }
    }
}
