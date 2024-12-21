package com.example.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Task> {
    public TaskListAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.taskTitleTextView);
        TextView descriptionTextView = convertView.findViewById(R.id.taskDescriptionTextView);
        TextView deadlineTextView = convertView.findViewById(R.id.taskDeadlineTextView);
        TextView statusTextView = convertView.findViewById(R.id.taskStatusTextView);

        titleTextView.setText(task.getTitle());
        descriptionTextView.setText(task.getDescription());
        deadlineTextView.setText(task.getDeadline());
        statusTextView.setText(task.getStatus());

        return convertView;
    }
}