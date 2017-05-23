package com.motondon.dagger2_demoapp.di.components;

import com.motondon.dagger2_demoapp.di.modules.MainActivityModule;
import com.motondon.dagger2_demoapp.di.scopes.PerActivityBeforeUserLoginScope;
import com.motondon.dagger2_demoapp.presentation.view.MainActivity;
import com.motondon.dagger2_demoapp.presentation.view.UserLoginFragment;

import dagger.Component;

/**
 * This component depends on the ApplicationComponent.
 * 
 * It uses only the MainActivityModule.
 *   
 * MainActivityModule provide instances of:
 *   - UserLoginPresenter 
 * 
 * It injects dependencies to the classes described below. 
 * - MainActivity 
 * - UserLoginFragment
 *
 * It uses @PerActivityBeforeUserLoginScope custom scope, since it is intended to be used when there is no logged user.
 *    
 */
@PerActivityBeforeUserLoginScope
@Component(
        dependencies = ApplicationComponent.class,
        modules = MainActivityModule.class
)

public interface MainActivityComponent {

    void inject(MainActivity activity);
    void inject(UserLoginFragment fragment);

    // Just as a note, we do not need to expose any method here, since this component is not being used as parent for any other component
}
