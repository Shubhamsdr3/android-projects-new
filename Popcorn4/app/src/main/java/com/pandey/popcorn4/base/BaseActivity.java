package com.pandey.popcorn4.base;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pandey.popcorn4.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
        } else {
            setContentView(R.layout.activity_base);
        }
        ButterKnife.bind(this);
    }

    public void startFragment(@NonNull Fragment fragment, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.main_container, fragment, fragment.getClass().getSimpleName());
        if(addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        } else {
            transaction.addToBackStack(null);
        }
        transaction.commitAllowingStateLoss();
    }

    protected abstract int getLayoutResId();

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
