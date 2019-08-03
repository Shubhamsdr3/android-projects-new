package com.pandey.popcorn4.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pandey.popcorn4.R;


public abstract class BaseFragment extends Fragment {

    @BindView(R.id.toolbar_container)
    ViewGroup vToolbarContainer;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_base, container, false);
        View inflatedView = inflater.inflate(getLayoutFile(), parentView.findViewById(R.id.main_container), false);
        ((FrameLayout)parentView.findViewById(R.id.main_container)).addView(inflatedView, 0);
        ButterKnife.bind(this, parentView);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        beforeInit();
        initLayout();
        initListeners();
        setUpToolbar();
        afterInit();
    }

    public void beforeInit() {
    }

    public abstract void initLayout();

    public abstract void initListeners();

    public void afterInit() {
    }

    private void setUpToolbar() {
        FrameLayout toolbar = getToolBar();
        if (toolbar != null) {
            vToolbarContainer.addView(toolbar);
        } else {
            vToolbarContainer.setVisibility(View.GONE);
        }
    }

    @Nullable
    public abstract FrameLayout getToolBar();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

//        Class listenerClass = getListenerClass();
//
//        if (listenerClass.isInstance(context)) {
//            mListener = listenerClass.cast(context);
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement Fragments context");
//        }

    }

//    @NonNull
//    protected abstract Class getListenerClass();

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @LayoutRes
    public abstract int getLayoutFile();

    public void startDialog(
            @NonNull DialogFragment fragment,
            boolean addToBackStack) {

        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        if (addToBackStack) {
            transaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        fragment.show(transaction, fragment.getClass().getSimpleName());
    }
}
