package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TaskDatabaseHelper dbHelper;
    private TaskListAdapter adapter;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskDatabaseHelper(this);
        ListView taskListView = findViewById(R.id.taskListView);
        searchEditText = findViewById(R.id.searchEditText);
        Button filterButton = findViewById(R.id.filterButton);

        List<Task> tasks = dbHelper.getAllTasks();
        adapter = new TaskListAdapter(this, tasks);
        taskListView.setAdapter(adapter);

        filterButton.setOnClickListener(v -> {
            String query = searchEditText.getText().toString().trim();
            List<Task> searchResults = dbHelper.searchTasks(query);
            adapter.clear();
            adapter.addAll(searchResults);
        });

        Button addTaskButton = findViewById(R.id.addTaskButton);
        addTaskButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        taskListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Task task = adapter.getItem(position);
            showTaskOptions(task);
            return true;
        });
    }

    private void showTaskOptions(Task task) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Options")
                .setItems(new CharSequence[]{"Modifier", "Supprimer"}, (dialog, which) -> {
                    if (which == 0) {
                        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                        intent.putExtra("task_id", task.getId());
                        intent.putExtra("task_title", task.getTitle());
                        intent.putExtra("task_description", task.getDescription());
                        intent.putExtra("task_deadline", task.getDeadline());
                        intent.putExtra("task_status", task.getStatus());
                        startActivity(intent);
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Confirmer la suppression")
                                .setMessage("Voulez-vous vraiment supprimer cette tâche ?")
                                .setPositiveButton("Oui", (dialogInterface, i) -> {
                                    dbHelper.deleteTask(task.getId());
                                    refreshTasks();
                                    Toast.makeText(this, "Tâche supprimée", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Non", null)
                                .show();
                    }
                })
                .show();
    }

    private void refreshTasks() {
        adapter.clear();
        adapter.addAll(dbHelper.getAllTasks());
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshTasks();
    }
}