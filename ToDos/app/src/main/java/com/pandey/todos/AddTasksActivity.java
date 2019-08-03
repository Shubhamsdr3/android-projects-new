package com.pandey.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.pandey.todos.data.AppDatabase;
import com.pandey.todos.data.TaskEntry;
import com.pandey.todos.model.AddTaskViewModel;
import com.pandey.todos.model.AddTaskViewModelFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddTasksActivity extends AppCompatActivity
{
    public  static final String EXTRA_TASK_ID = "extraTaskId";
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    public static final int DEFAULT_TASK_ID = -1;

    EditText editText;

    RadioGroup radioGroup;

    Button button;

    private int mTaskId = DEFAULT_TASK_ID;
    private AppDatabase appDatabase;
    Spinner taskSpinner;
    String taskType;

    private static final String LOG_TAG = AddTasksActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(LOG_TAG, "Inside onCreate method..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);

        editText =  findViewById(R.id.editTextTaskDescription);
        radioGroup = findViewById(R.id.radioGroup);
        button = findViewById(R.id.saveButton);

        // getting add-tasks view
        initView();

        appDatabase = AppDatabase.getInstance(getApplicationContext());


        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }

        // getting intent from HomeActivity
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID))
        {
            button.setText(getString(R.string.update_button));

            if (mTaskId == DEFAULT_TASK_ID)
            {
                final int taskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);

                AddTaskViewModelFactory factory = new AddTaskViewModelFactory(appDatabase, taskId);

                final AddTaskViewModel viewModel = ViewModelProviders.of(this, factory).get(AddTaskViewModel.class);

                viewModel.getTaskEntryLiveData().observe(this, new Observer<TaskEntry>()
                {
                    @Override
                    public void onChanged(TaskEntry taskEntry)
                    {
                        viewModel.getTaskEntryLiveData().removeObserver(this);
                        Log.i(LOG_TAG, "Updating the task with ID: " +  taskEntry.getId());
                        populateUI(taskEntry);
                    }
                });

            }
        }

        addItemOnSpinner();

        taskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                taskType = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putInt(INSTANCE_TASK_ID, mTaskId);
        super.onSaveInstanceState(outState);
    }


    private void initView()
    {
        Log.i(LOG_TAG, "Initializing views..");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onSaveButtonClicked();
            }
        });
    }

    // when save button is clicked
    private void onSaveButtonClicked() {
        String description = editText.getText().toString();
        if (TextUtils.isEmpty(description)) {
            Toast.makeText(getBaseContext(), "Enter a task description", Toast.LENGTH_SHORT).show();
            return;
        }
        int priority = getPriorityFromViews();
        Date date = new Date();
        final TaskEntry taskEntry = new TaskEntry(description, priority, date, taskType);

        Observable.just(taskEntry)
                .subscribeOn(Schedulers.io())
                .subscribe(new io.reactivex.Observer<TaskEntry>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TaskEntry taskEntry) {

                        if (mTaskId == DEFAULT_TASK_ID) {
                            // insert task to the database
                            appDatabase.taskDao().insertTask(taskEntry);
                            Log.i(LOG_TAG, "saving data to the database..");
                        } else {
                            taskEntry.setId(mTaskId);
                            appDatabase.taskDao().updateTask(taskEntry);
                        }
                        //Toast.makeText(getBaseContext(), "Data saved!", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // getting value from RadioButton
    public int getPriorityFromViews() {
        int priority = 1;

        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();

        if(checkedId == -1)
        {
            Toast.makeText(getBaseContext(), "Set a priority", Toast.LENGTH_SHORT).show();
            return 0;
        }

        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }

    public void setPriorityInViews(int priority)
    {
        switch (priority)
        {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }

    private void populateUI(TaskEntry task)
    {
        if(task == null)
        {
            return;
        }

        editText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
    }

    public  void addItemOnSpinner()
    {
        taskSpinner = (Spinner) findViewById(R.id.tasks_spinner);

        List<String> list = new ArrayList<String>();

        list.add("Personal");
        list.add("Work");
        list.add("Home");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskSpinner.setAdapter(dataAdapter);
    }

}