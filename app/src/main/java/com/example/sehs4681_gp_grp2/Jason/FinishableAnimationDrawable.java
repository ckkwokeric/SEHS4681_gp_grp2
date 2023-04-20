package com.example.sehs4681_gp_grp2.Jason;

import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;

public class FinishableAnimationDrawable extends AnimationDrawable {
    private OnFinishListener onFinishListener;
    private Handler handler;
    private Runnable runnable;

    public interface OnFinishListener {
        void onAnimationFinished();
    }

    public FinishableAnimationDrawable(AnimationDrawable animationDrawable, OnFinishListener onFinishListener) {
        for (int i = 0; i < animationDrawable.getNumberOfFrames(); i++) {
            addFrame(animationDrawable.getFrame(i), animationDrawable.getDuration(i));
        }
        setOneShot(animationDrawable.isOneShot());
        this.onFinishListener = onFinishListener;
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (onFinishListener != null) {
                    onFinishListener.onAnimationFinished();
                }
            }
        };
    }

    @Override
    public void start() {
        super.start();
        handler.postDelayed(runnable, getTotalDuration());
    }

    private int getTotalDuration() {
        int totalDuration = 0;
        for (int i = 0; i < getNumberOfFrames(); i++) {
            totalDuration += getDuration(i);
        }
        return totalDuration + 3000; // Add 3-second delay to the animation
    }
}
