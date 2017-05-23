package com.motondon.dagger2_demoapp.domain.interactor;

import android.util.Log;

import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskListPresenterImpl;
import com.motondon.dagger2_demoapp.service.EmailServiceException;
import com.motondon.dagger2_demoapp.service.EmailServiceProvider;

import java.util.concurrent.Executors;

public class SendTaskByEmailInteractor {
    private static final String TAG = SendTaskByEmailInteractor.class.getSimpleName();

    private EmailServiceProvider emailServiceProvider;

    public SendTaskByEmailInteractor(EmailServiceProvider emailServiceProvider) {
        this.emailServiceProvider = emailServiceProvider;
    }

    public void execute(String from, String to, String message, TaskListPresenterImpl.SendEmailCallback sendEmailCallback) {
        Log.d(TAG, "execute() - from: " + from + " - to: " + to);

        // We could also inject executor by using Dagger2!!!
        Executors.newSingleThreadExecutor().execute(() -> {

        	try {

                // Request provider to send the email
                emailServiceProvider.sendEmail(from, to, message);

                // If no exception was thrown, just return a successful event.
                Log.d(TAG, "sendEmail() - Email sent successfully");
                sendEmailCallback.emailSentSuccessfully();
                return;

            } catch (EmailServiceException e) {
            	Log.d(TAG, "sendEmail() - Failure when sending an email: " + e.getMessage());
            	sendEmailCallback.sendEmailFailure("Failure when sending an email.");
            }
        });
    }
}
