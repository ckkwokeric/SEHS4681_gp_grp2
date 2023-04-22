package com.example.sehs4681_gp_grp2.Harry;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.R;


public class Level1Fragment extends Fragment implements MazeView.MazeViewListener, SensorEventListener{

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private MazeView mazeView;

    private Button winButton;

    public Level1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level1, container, false);

        // Initialize sensor manager and accelerometer
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Other initialization code...
        mazeView = view.findViewById(R.id.mazeView);
        mazeView.setMazeViewListener(this);

        winButton = view.findViewById(R.id.win_button);
        winButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Your action here
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.flFragment, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }


        public void onWin() {
        winButton.setVisibility(View.VISIBLE);
    }

    public void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener( this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed for this game
    }

    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];

        // Update ball position in MazeView
        MazeView mazeView = getActivity().findViewById(R.id.mazeView);
        mazeView.updateBallPosition(-x, y);
    }

}