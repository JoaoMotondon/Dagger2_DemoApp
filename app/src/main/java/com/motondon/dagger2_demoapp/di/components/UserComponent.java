package com.motondon.dagger2_demoapp.di.components;

import android.content.Context;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.di.modules.RepositoriesModule;
import com.motondon.dagger2_demoapp.di.modules.ServicesModule;
import com.motondon.dagger2_demoapp.di.modules.UserModule;
import com.motondon.dagger2_demoapp.di.scopes.UserScope;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.service.EmailServiceProvider;
import com.motondon.dagger2_demoapp.service.TwitterServiceProvider;

import dagger.Component;

/**
 * This component depends on the ApplicationComponent.
 * 
 * It uses following modules:
 *   - UserModule
 *   - RepositoriesModule
 *   - ServicesModule
 *   
 * UserModule provides instances of:
 *   - UserModel (provide information about the current logged user).
 *   
 * RepositoriesModule provides instances of:
 *   - TaskRepositoryImpl (give access to the task repository)
 * 
 * ServicesModule provides instances of:
 *   - EmailServiceProvider
 *   - TwitterServiceProvider
 *   
 * ------------------------------------
 * 
 * This component is created after a successful login or when opening the app and it detects an user was left logged-in. It is intended to
 * be available while an user is logged-in. When the user is logged out, it is our responsibility to nullify it and release all references it holds
 * 
 * We are exposing some bindings in order for they to be accessible on the children components.
 * 
 */
@UserScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = {
                UserModule.class,
                RepositoriesModule.class,
                ServicesModule.class
        }
)
public interface UserComponent {

    // Expose to sub-graphs methods that come from ApplicationModule:
    //
    Context context();
    //
    // This is used by the activities when logout current user
    UserSessionRepositoryImpl userSessionRepositoryImpl();
    //
    // This is used by the activities to fetch information about the current logged in user
    UserRepositoryImpl provideUserRepository();
    //
    //
    // Expose to sub-graphs methods from UserComponent:
    //
    // This is used by the TaskListFragment in order to add the current logged user name to the toolbar
    UserModel provideUser();
    //
    // This is used by the task fragment to make operations with tasks
    TaskRepositoryImpl provideTaskRepository();
    //
    // Used by the TaskListFragment when need to send a task by email
    EmailServiceProvider provideEmailServiceProvider();
    //
    // Used by the TaskListFragment when need to send a task to twitter
    TwitterServiceProvider provideTwitterServiceProvider();
}
