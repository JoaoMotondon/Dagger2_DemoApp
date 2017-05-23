package com.motondon.dagger2_demoapp.di.components;

import com.motondon.dagger2_demoapp.di.modules.ActivityModule;
import com.motondon.dagger2_demoapp.di.scopes.PerActivityAfterUserLoginScope;
import com.motondon.dagger2_demoapp.presentation.view.TaskCreateFragment;
import com.motondon.dagger2_demoapp.presentation.view.TaskListFragment;

import dagger.Component;

/**
 * This component depends on the UserComponent.
 * 
 * It uses ActivityModule module. ActivityModule provides instances of presenter implementation classes used when a fragment/activity needs to
 * access domain layer.
 * 
 */
@PerActivityAfterUserLoginScope
@Component(
        dependencies = UserComponent.class,
        modules = ActivityModule.class
)

public interface ActivityComponent {
	
    // Injectors for Task fragments
    void inject(TaskListFragment taskListFragment);
    void inject(TaskCreateFragment taskCreateFragment);

    // Just as a note, we do not need to expose any method here, since this component is not being used as parent for any other component
}
