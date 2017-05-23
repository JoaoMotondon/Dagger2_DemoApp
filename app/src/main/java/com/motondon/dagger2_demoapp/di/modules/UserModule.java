package com.motondon.dagger2_demoapp.di.modules;

import com.motondon.dagger2_demoapp.di.scopes.UserScope;
import com.motondon.dagger2_demoapp.model.user.UserModel;

import dagger.Module;
import dagger.Provides;

@Module
public class UserModule {

	private UserModel userModel;

	public UserModule(UserModel userModel) {
		this.userModel = userModel;
	}

	@Provides
	@UserScope
	UserModel provideUser() {
		return this.userModel;
	}
}
