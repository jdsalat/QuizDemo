package com.machinetest.quiz.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.machinetest.quiz.R;
import com.machinetest.quiz.fragments.GKTestFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.title_quiz));
        replaceFragment(R.id.frame_content, GKTestFragment.newInstance(), GKTestFragment.TAG);
    }

    public void replaceFragment(int containerViewId, Fragment fragment, String TAG) {
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, TAG);
        fragmentTransaction.commit();
    }
}
