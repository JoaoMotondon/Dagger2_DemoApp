package com.motondon.dagger2_demoapp.presentation.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.model.task.TaskModel;
import com.motondon.dagger2_demoapp.presentation.presenter.task.TaskCreatePresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskCreateFragment extends Fragment implements TaskCreatePresenter.View {
    public static final String TAG = TaskCreateFragment.class.getSimpleName();

    @BindView(R.id.tvTaskName) TextView tvTaskName;
    @BindView(R.id.tvTaskDetails) TextView tvTaskDetails;

    @Inject
    TaskCreatePresenter taskCreatePresenter;

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

        View root = inflater.inflate(R.layout.fragment_task_create, container, false);
        ButterKnife.bind(this, root);

        // Request to the ActivityComponent to inject dependencies on this class. Basically it will inject presenter object
        ((TaskCreateActivity)getActivity()).getComponent().inject(this);
        
        Log.d(TAG, "onCreateView() - End");

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu()");

        inflater.inflate(R.menu.menu_task_create, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_confirm_new_task:
                Log.d(TAG, "onOptionsItemSelected() - menu_confirm_new_task clicked");

                TaskModel newTask = new TaskModel(tvTaskName.getText().toString(), tvTaskDetails.getText().toString());
                taskCreatePresenter.createTask(newTask, this);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

	@Override
	public void taskCreatedSuccessfully(TaskModel task) {
        Log.d(TAG, "taskCreatedSuccessfully() - Task: " + task.getName());

		getActivity().runOnUiThread(() -> {
			Toast.makeText(getContext(), "Task " + task.getName() + " created successfully", Toast.LENGTH_SHORT).show();
		});
		
		getActivity().finish();
	}

	@Override
	public void requestFailure(String errorMessage) {
        Log.d(TAG, "requestFailure() - errorMessage: " + errorMessage);

		getActivity().runOnUiThread(() -> {
			Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();	
		});
	}
}
