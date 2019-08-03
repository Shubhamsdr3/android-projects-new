package com.pandey.todos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pandey.todos.alltasks.Task;
import com.pandey.todos.data.AppDatabase;
import com.pandey.todos.data.UsersDatabase;
import com.pandey.todos.model.Injection;
import com.pandey.todos.model.UserViewModel;
import com.pandey.todos.model.ViewModelFactory;

import java.sql.Time;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

   @BindView(R.id.et_user_name)
   EditText vUserName;

   @BindView(R.id.et_user_password)
   EditText vUserPassword;

   @BindView(R.id.submit_user)
    Button vUserSubmitButton;

   UserViewModel  mUserViewModel;

   ViewModelFactory mViewModelFactory;

   private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Timber.i("Inside OnCreate method..");
        ButterKnife.bind(this);
        mViewModelFactory = Injection.provideViewModelFactory(getApplicationContext());
        mUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UserViewModel.class);
    }

    @OnClick(R.id.submit_user)
    public void onSubmitButtonClickec(){
        updateUserInDb();
    }

    private void updateUserInDb(){
        Timber.i("Updating the user to db..");
        String userName = vUserName.getText().toString();
        String userPassword = vUserPassword.getText().toString();
        vUserSubmitButton.setEnabled(false);
        mDisposable.add(mUserViewModel.updateUser(userName, userPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> vUserSubmitButton.setEnabled(true),
                        throwable -> Timber.e(throwable, "Unable to update user name")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Timber.i("Inside onStart method....");
        mDisposable.add(mUserViewModel.getUserName()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe( userName -> vUserName.setText(userName),
                                throwable -> Timber.e( "Unable to update user name")));
        mDisposable.add(mUserViewModel.getUserPassword()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(userPassword -> vUserPassword.setText(userPassword),
                throwable -> Timber.e(TAG, "Unable to update the password")));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Timber.i("Inside onStop method....");
        mDisposable.clear();
    }
}
