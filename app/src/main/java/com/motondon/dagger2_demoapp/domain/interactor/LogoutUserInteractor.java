package com.motondon.dagger2_demoapp.domain.interactor;

import com.motondon.dagger2_demoapp.data.repository.TaskRepositoryImpl;
import com.motondon.dagger2_demoapp.data.repository.UserSessionRepositoryImpl;
import com.motondon.dagger2_demoapp.model.user.UserSession;

public class LogoutUserInteractor {
    private static final String TAG = LogoutUserInteractor.class.getSimpleName();

    private TaskRepositoryImpl taskRepository;
    private UserSessionRepositoryImpl userSessionRepository;

    public LogoutUserInteractor(TaskRepositoryImpl taskRepository, UserSessionRepositoryImpl userSessionRepository) {
        this.taskRepository = taskRepository;
        this.userSessionRepository = userSessionRepository;
    }

    public void execute(String userName) {
    	UserSession userSession = new UserSession();
        userSessionRepository.setUserSession(userSession);
    }
}
