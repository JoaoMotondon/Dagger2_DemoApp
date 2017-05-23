package com.motondon.dagger2_demoapp.domain.interactor;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.TaskException;
import com.motondon.dagger2_demoapp.model.task.TaskModel;

import java.util.List;

public class FetchTaskListInteractor {
    private static final String TAG = FetchTaskListInteractor.class.getSimpleName();

    private TaskRepositoryImpl taskRepository;

    public FetchTaskListInteractor(TaskRepositoryImpl taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskModel> execute(String userName) throws TaskException {
        Log.d(TAG, "execute() - userName: " + userName);

        try {
        	
        	// We are making this request in the main thread, but in a real application we should make it in another thread,
        	// since data may come from a local database, or even from an external server, and we do not want to freeze
        	// our screen while waiting for the task list.
        	List<TaskModel> taskList = taskRepository.getTaskList(userName);
        	return taskList;
        	
        } catch (TaskException e) {
            Log.e(TAG, "execute() - Failure when fetching tasks for user: " + userName + ": " + e.getMessage());
            throw e;
        } catch (Exception e) {
            Log.e(TAG, "execute() - Failure when fetching tasks for user: " + userName + ": " + e.getMessage());
            throw new TaskException("Failure when fetching tasks for user: " + userName);
        }
    }
}
