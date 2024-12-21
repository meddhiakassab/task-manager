package com.example.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {
    private TaskDatabaseHelper dbHelper;
    private int taskId = -1;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText deadlineEditText;
    private Spinner statusSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new TaskDatabaseHelper(this);

        titleEditText = findViewById(R.id.taskTitleEditText);
        descriptionEditText = findViewById(R.id.taskDescriptionEditText);
        deadlineEditText = findViewById(R.id.taskDeadlineEditText);
        statusSpinner = findViewById(R.id.taskStatusSpinner);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_array, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("task_id")) {
            taskId = intent.getIntExtra("task_id", -1);
            titleEditText.setText(intent.getStringExtra("task_title"));
            descriptionEditText.setText(intent.getStringExtra("task_description"));
            deadlineEditText.setText(intent.getStringExtra("task_deadline"));

            String status = intent.getStringExtra("task_status");
            int statusPosition = statusAdapter.getPosition(status);
            statusSpinner.setSelection(statusPosition);
        }

        Button saveTaskButton = findViewById(R.id.saveTaskButton);
        saveTaskButton.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();
        String deadline = deadlineEditText.getText().toString().trim();
        String status = statusSpinner.getSelectedItem().toString();

        if (title.isEmpty()) {
            Toast.makeText(this, "Le titre est obligatoire", Toast.LENGTH_SHORT).show();
            return;
        }

        if (taskId == -1) {
            long result = dbHelper.insertTask(title, description, deadline);
            if (result != -1) {
                Toast.makeText(this, "Tâche ajoutée avec succès", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur lors de l'ajout de la tâche", Toast.LENGTH_SHORT).show();
            }
        } else {
            dbHelper.updateTask(taskId, title, description, deadline, status);
            Toast.makeText(this, "Tâche mise à jour avec succès", Toast.LENGTH_SHORT).show();
        }

        finish();
    }
}