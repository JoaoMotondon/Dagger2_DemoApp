package com.motondon.dagger2_demoapp.di.components;

import android.content.Context;

import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.di.modules.ApplicationModule;
import com.motondon.dagger2_demoapp.presentation.view.BaseActivity;
import com.motondon.dagger2_demoapp.presentation.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is the main DI component. All the other DI components depends on it (directly or indirectly).
 * 
 * After the graph creation, it will look like this:
 *  
 *  | 
 *  +----> ApplicationComponent
 *  |  
 *  +----------> MainActivityComponent
 *  |
 *  +----------> UserSessionComponent
 *     |
 *     +------------> ActivityComponent
 * 
 * It will be created by the Dagger2DemoAppApplication class and it uses @Singleton standard scope.
 * 
 * It uses only ApplicationModule module
 * 
 * ApplicationModule provides instances of:
 *   - Context (used everywhere)
 *   - UserSessionRepositoryImpl (used to check whether there is already an user session when opening the app)
 *   - UserRepositoryImpl (give access to the user repository)
 *   
 * Since our DI graph uses dependencies (instead sub-component), we have to explicitly expose all methods we want children component can have access to.
 * 
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

	void inject(BaseActivity baseActivity);
	void inject(MainActivity mainActivity);

	// Expose to sub-graphs methods from ApplicationModule:
	Context context();
	UserSessionRepositoryImpl userSessionRepository();
	UserRepositoryImpl userRepository();
}
