package com.motondon.dagger2_demoapp.presentation.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.application.Dagger2DemoAppApplication;
import com.motondon.dagger2_demoapp.model.task.TaskModel;
import com.motondon.dagger2_demoapp.model.user.UserModel;
import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskListPresenter;
import com.motondon.dagger2_demoapp.service.TwitterServiceException;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TaskListFragment extends Fragment implements TaskListPresenter.View {
    public static final String TAG = TaskListFragment.class.getSimpleName();
    
    @BindView(R.id.tasklist_container) CoordinatorLayout coordinatorLayoutContainer;
    @BindView(R.id.lvTaskList) RecyclerView recyclerViewTaskList;

    private ProgressDialog mProgressDialog;
    
    @Inject
    TaskListPresenter taskListPresenter;

    @Inject
    UserModel userModel;
      
    private TaskListAdapter adapter;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() - Begin");

        super.onCreate(savedInstanceState);

        // Enable menu
        this.setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() - Begin");

        View root = inflater.inflate(R.layout.fragment_task_list, container, false);
        ButterKnife.bind(this, root);

        // Request to the ActivityComponent to inject dependencies on this class.
        ((TaskListActivity)getActivity()).getComponent().inject(this);

        // Show the user name on the title
        getActivity().setTitle(userModel.getName());
        
        // initialize the adapter
        adapter = new TaskListAdapter(getContext(), this);

        recyclerViewTaskList.setHasFixedSize(true);
        recyclerViewTaskList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerViewTaskList.setAdapter(adapter);
        
        Log.d(TAG, "onCreateView() - End");

        return root;
    }

    @Override
    public void onResume() {
    	Log.d(TAG, "onResume()");
    	super.onResume();
    
    	displayTasks();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu()");

        inflater.inflate(R.menu.main_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected()");

        switch (item.getItemId()) {

            case R.id.menu_logout:
                Log.d(TAG, "onOptionsItemSelected() - logout clicked");
                taskListPresenter.logoutUser();
                Log.d(TAG, "onOptionsItemSelected() - Finishing activity...");
                
                // TODO: Here we should clean up all the stuff related to the user being logging off.
                // Release our UserComponent, since user is being logged out and this component has @UserScope, so there is no sense to keep anything related to it.
                ((Dagger2DemoAppApplication)getActivity().getApplication()).releaseUserComponent();
                
                // Then finish the fragment.
                getActivity().finish();
                
                // And finally launch UserLoginActivity activity
                Intent intent = new Intent(getContext(), UserLoginActivity.class);
                startActivity(intent);
                
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.fab_add_task)
    public void onAddTaskFabButtonClick() {
        Log.d(TAG, "onAddTaskFabButtonClick() - Calling TaskCreateActivity activity...");

        Intent i = new Intent(getActivity(), TaskCreateActivity.class);
        startActivity(i);
    }
    
    private void displayTasks() {
        Log.d(TAG, "displayTasks()");

        // Request the presenter a list of tasks for the current user and add it to the adapter.
    	List<TaskModel> taskList = taskListPresenter.fetchTaskList();
        adapter.setTaskList(taskList);
	}
  
    public void deleteTask(TaskModel task) {
        Log.d(TAG, "deleteTask() - Task: " + task.getName());
    	taskListPresenter.deleteTask(task, this);
    }

    public void sendEmail(TaskModel task) {
        Log.d(TAG, "sendEmail() - Task: " + task.getName());
        taskListPresenter.sendEmail(task, this);
    }

	public void sendTweet(TaskModel task) {
        Log.d(TAG, "sendTweet() - Task: " + task.getName());

        try {
            // Notice we are doing it synchronously, but in a real application it would be better to send a tweed asynchronously. See sendEmail process
            // to get an idea
            taskListPresenter.sendTweet(task);

            Toast.makeText(getContext(), "Tweet sent successfully", Toast.LENGTH_SHORT).show();
        } catch (TwitterServiceException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
	}
	
	@Override
	public void taskDeletedSuccessfully(TaskModel task) {
        Log.d(TAG, "taskDeletedSuccessfully() - Task: " + task.getName());

		getActivity().runOnUiThread(() -> {
			Toast.makeText(getContext(), "Task " + task.getName() + " deleted successfully", Toast.LENGTH_SHORT).show();
			adapter.removeTask(task);
		});
	}

	@Override
	public void requestFailure(String errorMessage) {
        Log.d(TAG, "requestFailure() - errorMessage: " + errorMessage);

		getActivity().runOnUiThread(() -> {
			Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();	
		});
	}
	
	@Override
	public void emailSentSuccessfully(TaskModel task) {
        Log.d(TAG, "emailSentSuccessfully() - Task: " + task.getName());

		getActivity().runOnUiThread(() -> {
			Toast.makeText(getContext(), "Email sent successfully", Toast.LENGTH_SHORT).show();
		});
	}

	@Override
	public void sendEmailFailure(String errorMessage) {
        Log.d(TAG, "sendEmailFailure() - errorMessage: " + errorMessage);

		getActivity().runOnUiThread(() -> {
			Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
		});
	}
	
	@Override
    public void showProgressDialog(String message) {
		Log.v(TAG, "showProgressDialog()");

		// Note we are not handling orientation change
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.show();
	}
	
	@Override
    public void hideProgressDialog() {
        Log.v(TAG, "hideProgressDialog()");

		if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
	}
}
