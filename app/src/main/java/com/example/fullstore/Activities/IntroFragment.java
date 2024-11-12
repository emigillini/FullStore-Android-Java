package com.example.fullstore.Activities;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fullstore.R;

public class IntroFragment extends BaseFragment {

    public IntroFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_intro, container, false);
        showToolbar(false);

        MotionLayout motionLayout = rootView.findViewById(R.id.motionLayout);

        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionStarted(MotionLayout motionLayout, int startId, int endId) {

            }

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int startId, int endId, float progress) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentId) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       replaceFragment(new LoginFragment());
                    }
                }, 2000);
            }

            @Override
            public void onTransitionTrigger(MotionLayout motionLayout, int triggerId, boolean positive, float progress) {

            }
        });


        return rootView;
    }
}