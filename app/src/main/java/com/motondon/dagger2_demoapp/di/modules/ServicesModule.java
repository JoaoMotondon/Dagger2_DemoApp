package com.motondon.dagger2_demoapp.di.modules;

import android.content.Context;

import com.motondon.dagger2_demoapp.di.scopes.UserScope;
import com.motondon.dagger2_demoapp.service.EmailServiceProvider;
import com.motondon.dagger2_demoapp.service.TwitterServiceProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

	@Provides
	@UserScope
	EmailServiceProvider provideEmailServiceProvider(Context context) {
		return new EmailServiceProvider(context);
	}
	
	@Provides
	@UserScope
	TwitterServiceProvider provideTwitterServiceProvider(Context context) {
		return new TwitterServiceProvider(context);
	}
}
