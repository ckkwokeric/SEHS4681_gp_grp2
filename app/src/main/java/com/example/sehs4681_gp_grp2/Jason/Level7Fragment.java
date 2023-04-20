package com.example.sehs4681_gp_grp2.Jason;

import static android.content.Context.SENSOR_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehs4681_gp_grp2.Harry.Level1Fragment;
import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.R;

public class Level7Fragment extends Fragment implements SensorEventListener, View.OnClickListener{

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float lastX, lastY, lastZ;
    private long lastUpdate;
    private static final int SHAKE_THRESHOLD = 1000;

    private ImageView eggImageView;
    private TextView completionTextView;
    private Button btn;

    public Level7Fragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_level7, container, false);

        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        eggImageView = view.findViewById(R.id.eggImageView);
        btn = view.findViewById(R.id.btn_next);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long currentTime = System.currentTimeMillis();
            if (currentTime - lastUpdate > 100) {
                long diffTime = currentTime - lastUpdate;
                lastUpdate = currentTime;

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    onShakeDetected();
                }

                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // Not needed for this example
    }

    private void onShakeDetected() {
        AnimationDrawable animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.egg_cracking_animation);
        FinishableAnimationDrawable finishableAnimationDrawable = new FinishableAnimationDrawable(animationDrawable, new FinishableAnimationDrawable.OnFinishListener() {
            @Override
            public void onAnimationFinished() {
                eggImageView.post(new Runnable() {
                    @Override
                    public void run() {
                        eggImageView.setImageResource(R.drawable.egg_crack_2);
                    }
                });
                Toast.makeText(getActivity(), "Congratulations! You found the chicken!", Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.VISIBLE);
            }
        });

        eggImageView.setImageDrawable(finishableAnimationDrawable);
        eggImageView.post(new Runnable() {
            @Override
            public void run() {
                finishableAnimationDrawable.start();
            }
        });

        //completionTextView.setVisibility(View.VISIBLE);

        // Optionally, add vibration feedback
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(500);
        }

        // Unregister the accelerometer listener to stop detecting shakes
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.flFragment, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}