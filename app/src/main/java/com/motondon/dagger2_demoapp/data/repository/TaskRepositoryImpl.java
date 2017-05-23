package com.motondon.dagger2_demoapp.data.repository;

import android.util.Log;

import com.motondon.dagger2_demoapp.common.Constants;
import com.motondon.dagger2_demoapp.domain.exception.TaskException;
import com.motondon.dagger2_demoapp.model.task.TaskModel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TaskRepositoryImpl {
    private static final String TAG = TaskRepositoryImpl.class.getSimpleName();

    private Hashtable<String, List<TaskModel>> mTaskListByUser = new Hashtable<>();
	private final String userAdmin1 = Constants.ADMIN_01_USER_NAME;
	private final String userAdmin2 = Constants.ADMIN_02_USER_NAME;

	public TaskRepositoryImpl() {
		Log.d(TAG, "ctor()");

		generateFakeData();
	}

    public boolean containTask(String userName, TaskModel task) throws TaskException {
		Log.d(TAG, "containTask()");

		if (!mTaskListByUser.containsKey(userName)) {
			throw new TaskException("User: " + userName + " not found.");
		}

		List<TaskModel> tasks = mTaskListByUser.get(userName);
		if (tasks == null ) {
			return false;
		}

		for(TaskModel t : tasks) {
			if (t.getName().equals(task.getName())) {
				return true;
			}
		}

        return false;
    }

    public void createTask(String userName, TaskModel newTask) throws TaskException {
		Log.d(TAG, "createTask()");

		if (!mTaskListByUser.containsKey(userName)) {
			throw new TaskException("User: " + userName + " not found.");
		}

		List<TaskModel> tasks = mTaskListByUser.get(userName);

		for(TaskModel t : tasks) {
			if (t.getName().equals(newTask.getName())) {
				throw new TaskException("Task: " + newTask.getName() + " already exists.");
			}
		}

		tasks.add(newTask);
    }

	public List<TaskModel> getTaskList(String userName) throws TaskException {
		Log.d(TAG, "getTaskList()");

		if (!mTaskListByUser.containsKey(userName)) {
			throw new TaskException("User: " + userName + " not found.");
		}

		List<TaskModel> tasks = mTaskListByUser.get(userName);
		return tasks;
	}

	public void deleteTask(String userName, TaskModel task) throws TaskException {
		Log.d(TAG, "deleteTask()");

		if (!mTaskListByUser.containsKey(userName)) {
			throw new TaskException("User: " + userName + " not found.");
		}

		List<TaskModel> tasks = mTaskListByUser.get(userName);

		boolean found = false;
		for(TaskModel t : tasks) {
			if (t.getName().equals(task.getName())) {
				tasks.remove(t);
				found = true;
				break;
			}
		}

		if (!found) {
			throw new TaskException("Task: " + task.getName() + " not found.");
		}
	}

	/**
	 * Generate a list with five tasks for each available users
	 *
	 */
	private void generateFakeData() {
		Log.d(TAG, "generateFakeData()");

		mTaskListByUser.clear();

		List<TaskModel> adminTasks = new ArrayList<>();
		adminTasks.add(new TaskModel("Task 1", "This is a description for task 1"));
		adminTasks.add(new TaskModel("Task 2", "This is a description for task 2"));
		adminTasks.add(new TaskModel("Task 3", "This is a description for task 3"));
		adminTasks.add(new TaskModel("Task 4", "This is a description for task 4"));

		List<TaskModel> supervisorTasks = new ArrayList<>();
		supervisorTasks.add(new TaskModel("Task A", "This is a description for task A"));
		supervisorTasks.add(new TaskModel("Task B", "This is a description for task B"));
		supervisorTasks.add(new TaskModel("Task C", "This is a description for task C"));
		supervisorTasks.add(new TaskModel("Task D", "This is a description for task D"));

		mTaskListByUser.put(userAdmin1, adminTasks);
		mTaskListByUser.put(userAdmin2, supervisorTasks);
	}
}
