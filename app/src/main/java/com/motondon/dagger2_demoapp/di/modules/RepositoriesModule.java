package com.motondon.dagger2_demoapp.di.modules;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.di.scopes.UserScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoriesModule {

    @Provides
    @UserScope
    TaskRepositoryImpl provideTaskRepository() {
        return new TaskRepositoryImpl();
    }
}
