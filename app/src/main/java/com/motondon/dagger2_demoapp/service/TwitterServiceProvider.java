package com.motondon.dagger2_demoapp.service;

import android.content.Context;
import android.util.Log;

public class TwitterServiceProvider {
private static final String TAG = TwitterServiceProvider.class.getSimpleName();
	
	private Context mContext;

	/**
	 * Since we are using Dagger2 Lazy injection to inject an instance of TwitterServiceProvider, we should see this log message only when user first requests
	 * itt. Next calls to send tweets will not call this construct, but use the same TwitterServiceProvider instance.
	 *
	 * @param context
	 */
	public TwitterServiceProvider(Context context) {
		Log.d(TAG, "ctor()");
		this.mContext = context;
	}
	
	public void sendTweet(String message) throws TwitterServiceException {
		Log.d(TAG, "sendTweet() - message: " + message);
		
		// Just add some delay in order to simulate a tweet being sent.
		// *** Notice we are sleeping in the main thread, which is a very bad practice. Do not do it in a real application! ***

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			Log.d(TAG, "sendTweet() - Failure when sending a tweet.");
			throw new TwitterServiceException("Failure when sending a tweet.");
		}

		Log.d(TAG, "sendTweet() - Tweet sent successfully");
	}
}
