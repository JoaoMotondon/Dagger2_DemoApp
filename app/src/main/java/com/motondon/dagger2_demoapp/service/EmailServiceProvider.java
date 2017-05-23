package com.motondon.dagger2_demoapp.service;

import android.content.Context;
import android.util.Log;

import java.util.concurrent.Executors;

public class EmailServiceProvider {
	private static final String TAG = EmailServiceProvider.class.getSimpleName();
	
	private Context mContext;
	boolean mServiceReady = false;

	/**
	 * Since we are using Dagger2 Lazy injection to inject an instance of EmailServiceProvider, it will be created only when user first requests to send an
     * email. This means this constructor will be called (by ServicesModule::provideEmailServiceProvider method ) when SendTaskByEmailInteractor::execute()
     * first calls Lazy<EmailServiceProvider>::get() method.
	 *
	 * Note we are sleeping in a separate thread, otherwise we could lock the main thread.
	 *
	 * @param context
	 */
	public EmailServiceProvider(Context context) {
		Log.d(TAG, "ctor()");
        this.mContext = context;

		// We could also inject executor by using Dagger2!!!
		Executors.newSingleThreadExecutor().execute(() -> {

			try {
				Log.d(TAG, "ctor() - Initializing email service provider. Simulating a 5 seconds delay...");

				// Wait for some time in order to simulate an email provider initialization.
				Thread.sleep(5000);

				Log.d(TAG, "ctor() - Email service provider initialized successfully");
				mServiceReady = true;

			} catch (InterruptedException e) {
				// Do nothing here, since this is just a simulation
			}
		});
	}
	
	public void sendEmail(String from, String to, String message) throws EmailServiceException {
		Log.d(TAG, "sendEmail() - from: " + from + " - to: " + to + " - message: " + message);

		// The first time this method is called, probably we are still simulating the service initialization. So we need to wait
		// until it is ready.
		while (!mServiceReady) {
			try {
				Log.d(TAG, "sendEmail() - Email service provider not initialized yet. Waiting a while...");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// Do nothing here, since this is just a simulation
			}
		}

		// Ok! Now the service is initialized. So send the email...
		Log.d(TAG, "sendEmail() - Detected email service provider is already initialized. Sending email...");
		doSendEmail();
	}

	private void doSendEmail() throws EmailServiceException {
		Log.d(TAG, "doSendEmail()");

		try {
			// Just wait for some time in order to simulate an email message being sent.
			Thread.sleep(350);
			Log.d(TAG, "doSendEmail() - Email sent successfully.");
			
		} catch (InterruptedException e) {
			// Do nothing here, since this is juts a simulation
		}
	}
}
