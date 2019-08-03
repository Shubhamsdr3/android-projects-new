package com.pandey.todos.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pandey.todos.AddTasksActivity;
import com.pandey.todos.AppExecutors;
import com.pandey.todos.R;
import com.pandey.todos.adapters.TaskAdapter;
import com.pandey.todos.alltasks.AllTasksPresenter;
import com.pandey.todos.alltasks.AllTasksPresenter.AllTasksView;
import com.pandey.todos.data.AppDatabase;
import com.pandey.todos.data.DbHelper;
import com.pandey.todos.data.TaskEntry;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;


public class  AllTaskFragment extends Fragment
        implements AllTasksView,  TaskAdapter.ItemClickListener {

    private static final String LOG_TAG = AllTaskFragment.class.getSimpleName();

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    private TaskAdapter taskAdapter;

    @Nullable
    private AppDatabase appDatabase;

    DbHelper dbHelper;

    @Nullable
    private AllTasksPresenter allTasksPresenter;

    @BindView(R.id.recyclerViewTasks)
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(getActivity());

        new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }
                    // Called when a user swipes left or right on a ViewHolder
                    @Override
                    public void onSwiped(final @NonNull RecyclerView.ViewHolder viewHolder, int swipeDir)
                    {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                DeleteTaskDialogFragment deleteTaskDialogFragment = new DeleteTaskDialogFragment();
                                // deleteTaskDialogFragment.show();
                                int position = viewHolder.getAdapterPosition();
                                List<TaskEntry> tasks = taskAdapter.getTasks();
                                appDatabase.taskDao().deleteTask(tasks.get(position));
                            }
                        });
                    }
                }).attachToRecyclerView(recyclerView);

        // Do assign value once you have initialized data
        appDatabase =  AppDatabase.getInstance(getContext());
        setUpViewModel();

        dbHelper = new DbHelper();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all_task, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        allTasksPresenter.loadAllTasks();
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }


    @Override
    public void displayAllTasks(List<TaskEntry> taskList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskAdapter =  new TaskAdapter(getContext(), this);
        recyclerView.setAdapter(taskAdapter);
    }

    @Override
    public void displayNoTask() {
    }

    private void setUpViewModel() {
        addDisposables(dbHelper.getAllTasks()
                .subscribe( taskEntries -> taskAdapter.setTasks(taskEntries)));
    }

    public void openDialog() {
        DialogFragment dialog = new DeleteTaskDialogFragment();
        dialog.show(getFragmentManager(), "DeleteTaskFragment");
    }

    @Override
    public void onItemClickListener(int itemId) {
        Intent intent = new Intent(this.getActivity(), AddTasksActivity.class);
        intent.putExtra(AddTasksActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }

    public interface AllTaskFragmentListener {
    }

    protected void addDisposables(Disposable... dArray) {
        if (dArray != null && dArray.length > 0) {
            this.disposables.addAll(dArray);
        }

    }

}