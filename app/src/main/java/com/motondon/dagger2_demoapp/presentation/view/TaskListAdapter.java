package com.motondon.dagger2_demoapp.presentation.view;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.motondon.dagger2_demoapp.R;
import com.motondon.dagger2_demoapp.model.task.TaskModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    public static final String TAG = TaskListAdapter.class.getSimpleName();
    
    private List<TaskModel> tasks = new ArrayList<>();

    private Context context;
    private TaskListFragment fragment;
    
    public TaskListAdapter(Context context, TaskListFragment fragment) {
        Log.d(TAG, "ctor()");

        this.context = context;
        this.fragment = fragment;
    }

    public void setTaskList(List<TaskModel> tasks) {
        Log.d(TAG, "setTaskList()");

        this.tasks.clear();

        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    public void removeTask(TaskModel task) {
        Log.d(TAG, "removeTask() - Task: " + task.getName());

        int taskPosition = getTaskPositionByName(task.getName());

        if (taskPosition > -1) {
            TaskModel t = tasks.get(taskPosition);
            this.tasks.remove(t);
            notifyItemRemoved(taskPosition);
        }
    }

    private int getTaskPositionByName(String name) {
        for (TaskModel t : tasks) {
            if (t.getName().equals(name)) {
                return tasks.indexOf(t);
            }
        }

        return -1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TaskModel task = tasks.get(position);

        holder.task = task;

        String firstLetter = task.getName().substring(0, 1).toUpperCase();
        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color1 = generator.getRandomColor();
        TextDrawable drawable = TextDrawable.builder().buildRound(firstLetter, color1);
        
        holder.imageView.setImageDrawable(drawable);
        holder.taskName.setText(task.getName());
        holder.taskDetails.setText(task.getDetails());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements 
    	View.OnCreateContextMenuListener,
    	MenuItem.OnMenuItemClickListener {

        private TaskModel task;

        @BindView(R.id.image_view) ImageView imageView;
        @BindView(R.id.task_name) TextView taskName;
        @BindView(R.id.task_details) TextView taskDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnCreateContextMenuListener(this);
        }
        
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, R.id.menu_task_remove_action, 0, "Delete Task").setOnMenuItemClickListener(this);
            menu.add(0, R.id.menu_task_email_action, 0, "Email Task").setOnMenuItemClickListener(this);
            menu.add(0, R.id.menu_task_send_tweet_action, 0, "Send Tweet").setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Log.d(TAG, "onMenuItemClick()");

            switch (item.getItemId()) {
                case R.id.menu_task_remove_action:
                    Log.d(TAG, "onMenuItemClick() - Clicked over delete item for task: " + task.getName());
                    showDeleteDialog();
                    return true;

                case R.id.menu_task_email_action:
                    Log.d(TAG, "onMenuItemClick() - Clicked over send email item for task: " + task.getName());
                    fragment.sendEmail(task);
                    return true;

                case R.id.menu_task_send_tweet_action:
                    Log.d(TAG, "onMenuItemClick() - Clicked over send tweet item for task: " + task.getName());
                    fragment.sendTweet(task);
                    return true;

                default:
                    return false;
            }
        }

        private void showDeleteDialog() {
            Log.d(TAG, "showDeleteDialog() - Task: " + task.getName());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Are you sure you want to delete this task?");
            builder.setPositiveButton("Yes",(dialog, which) -> {
                // If user confirm delete task, process it.
                Log.d(TAG, "showDeleteDialog() - Deleting task: " + task.getName() + "...");
                fragment.deleteTask(task);
            });

            builder.setNegativeButton("No", (dialog, which) -> {/* Do nothing here! */ });

            AlertDialog dialog = builder.create();
            // display dialog
            dialog.show();
        }
    }
}