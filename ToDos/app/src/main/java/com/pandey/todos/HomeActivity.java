package com.pandey.todos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pandey.todos.fragment.AllTaskFragment;
import com.pandey.todos.fragment.PersonalTaskFragments;
import com.pandey.todos.fragment.WorkTaskFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeActivity extends AppCompatActivity implements
         AllTaskFragment.AllTaskFragmentListener, PersonalTaskFragments.PersonalTaskListener {

    private static final String LOG_TAG = HomeActivity.class.getSimpleName();

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    @BindView(R.id.all_task)
    TextView allTaskView;

    @BindView(R.id.personal_tasks)
    TextView personalTaskView;

    @BindView(R.id.work_tasks)
    TextView workTaskView;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "Inside onCreate method..");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        ButterKnife.bind(this);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddTasksActivity.class);
                startActivity(intent);
            }
        });
    }


    @OnClick(R.id.all_task)
    public void onAllTaskClicked(){
        Fragment fragment = new AllTaskFragment();
        fragmentTransaction.replace(R.id.task_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.personal_tasks)
    public void onPersonalTaskClicked(){
        Fragment fragment =  new PersonalTaskFragments();
        fragmentTransaction.replace(R.id.personal_tasks, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.work_tasks)
    public void onWorkTaskClicked(){
        Fragment fragment = new WorkTaskFragment();
        fragmentTransaction.replace(R.id.work_tasks, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        Log.i(LOG_TAG, "Inside onResume method..");
        super.onResume();
    }
}