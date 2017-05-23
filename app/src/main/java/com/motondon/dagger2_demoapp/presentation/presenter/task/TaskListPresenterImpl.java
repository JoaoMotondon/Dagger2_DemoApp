package com.motondon.dagger2_demoapp.presentation.presenter.task;

import android.util.Log;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.domain.exception.TaskException;
import com.motondon.dagger2_demoapp.domain.interactor.DeleteTaskInteractor;
import com.motondon.dagger2_demoapp.domain.interactor.FetchTaskListInteractor;
import com.motondon.dagger2_demoapp.domain.interactor.LogoutUserInteractor;
import com.motondon.dagger2_demoapp.domain.interactor.SendTaskByEmailInteractor;
import com.motondon.dagger2_demoapp.model.task.TaskModel;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.service.EmailServiceProvider;
import com.motondon.dagger2_demoapp.service.TwitterServiceException;
import com.motondon.dagger2_demoapp.service.TwitterServiceProvider;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;

public class TaskListPresenterImpl implements TaskListPresenter {

    // This interface is used when sending a task by email. It will be passed on to the SendTaskByEmailInteractor::execute() method in order to execute its task
    // asynchronously and send back the result
    public interface SendEmailCallback {
        void emailSentSuccessfully();
        void sendEmailFailure(String errorMessage);
    }

	private static final String TAG = TaskListPresenterImpl.class.getSimpleName();

    @Inject
    UserSessionRepositoryImpl userSessionRepository;
    
    @Inject
    UserRepositoryImpl userRepository;

    @Inject
    TaskRepositoryImpl taskRepository;

    // Using Lazy interface, this attribute will only be injected when we call Lazy<t>::get() method
    @Inject
    Lazy<EmailServiceProvider> emailServiceProvider;

    // Using Lazy interface, this attribute will only be injected when we call Lazy<t>::get() method
    @Inject
    Lazy<TwitterServiceProvider> twitterServiceProvider;
    
    @Inject
    UserModel userModel;

    // We need to explicitly add this constructor in order to add @Inject dagger annotation, otherwise Dagger will not know how to construct it.
    @Inject
    public TaskListPresenterImpl() {
    }
    
    @Override
    public void logoutUser() {
        Log.d(TAG, "logoutUser()");
        LogoutUserInteractor logoutUserInteractor = new LogoutUserInteractor(taskRepository, userSessionRepository);
        logoutUserInteractor.execute(userModel.getName());
    }

	@Override
	public UserModel getUser(String userEmail) {
        Log.d(TAG, "getUser() - userEmail: " + userEmail);

		return userRepository.getUser(userEmail);
	}

    @Override
    public List<TaskModel> fetchTaskList() {
        Log.d(TAG, "fetchTaskList()");

    	// Fetch all the tasks for the current logged user and return it.
    	FetchTaskListInteractor fetchTaskListInteractor = new FetchTaskListInteractor(taskRepository);

        List<TaskModel> taskList = null;
        try {
            taskList = fetchTaskListInteractor.execute(userModel.getName());
        } catch (TaskException e) {
            e.printStackTrace();
        }

        return taskList;
    }

	@Override
	public void deleteTask(TaskModel task, TaskListPresenter.View view) {
        Log.d(TAG, "deleteTask() - Task: " + task.getName());

		DeleteTaskInteractor deleteTaskInteractor = new DeleteTaskInteractor(taskRepository);
		
		try {
			deleteTaskInteractor.execute(userModel.getName(), task);
            Log.d(TAG, "deleteTask() - Task: " + task.getName() + " deleted successfully");
			view.taskDeletedSuccessfully(task);
			
		} catch (TaskException e) {
            Log.e(TAG, "deleteTask() - Failure when trying to delete task: " + task.getName() + ": " + e.getMessage());
			view.requestFailure(e.getMessage());
		}
	}

	@Override
	public void sendEmail(TaskModel task, View view) {
		Log.d(TAG, "emailTask() - task: " + task.getName());
		
		view.showProgressDialog("Sending email...");

        // This demonstrates how to lazy injects a member when using Dagger2.
        // EmailServiceProvider will only be created when we first call "Lazy<EmailServiceProvider>.get()" method.
        // Next calls to "Lazy<EmailServiceProvider>.get()" will return the same instance.
        SendTaskByEmailInteractor sendTaskByEmailInteractor = new SendTaskByEmailInteractor(emailServiceProvider.get());

        // This method will be executed in a separate thread, in order to make progress dialog visible while this task is being executed.
        sendTaskByEmailInteractor.execute("myEmail@mycompany.com", "toEmail@mycompany.com", "This is just a sample message for task: " + task.getName(), new SendEmailCallback() {
            @Override
            public void emailSentSuccessfully() {
                view.hideProgressDialog();
                view.emailSentSuccessfully(task);
            }

            @Override
            public void sendEmailFailure(String errorMessage) {
                Log.e(TAG, "deleteTask() - Failure when trying to delete task: " + task.getName() + ": " + errorMessage);
                view.hideProgressDialog();
                view.sendEmailFailure(errorMessage);
            }
        });
	}

	@Override
	public void sendTweet(TaskModel task) throws TwitterServiceException {
		Log.d(TAG, "sendTweet() - task: " + task.getName());

        // This is when Dagger will really inject a TwitterServiceProvider member, since we used Lazy<t> when declaring it.
		twitterServiceProvider.get().sendTweet("This is just a sample message for task: " + task.getName());
	}
}
