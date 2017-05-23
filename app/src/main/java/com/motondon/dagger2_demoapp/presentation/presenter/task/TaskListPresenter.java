package com.motondon.dagger2_demoapp.presentation.presenter.task;

import com.motondon.dagger2_demoapp.model.task.TaskModel;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.service.TwitterServiceException;

import java.util.List;

public interface TaskListPresenter {

	interface View {
        void taskDeletedSuccessfully(TaskModel task);
        void requestFailure(String errorMessage);
        
        void emailSentSuccessfully(TaskModel task);
        void sendEmailFailure(String errorMessage);
        
		void showProgressDialog(String message);
		void hideProgressDialog();
	}
	
    void logoutUser();
	UserModel getUser(String userEmail);

    List<TaskModel> fetchTaskList();
    void deleteTask(TaskModel task, View view);

	void sendEmail(TaskModel task, View view);
	void sendTweet(TaskModel task) throws TwitterServiceException;
}
