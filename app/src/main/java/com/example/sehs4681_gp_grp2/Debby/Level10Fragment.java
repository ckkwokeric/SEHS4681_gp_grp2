package com.example.sehs4681_gp_grp2.Debby;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehs4681_gp_grp2.DBHelper;
import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.Model.User;
import com.example.sehs4681_gp_grp2.R;


public class Level10Fragment extends Fragment {

    private DBHelper dbHelper;
    Button b_main, b_next_game;
    long startTime, endTime, currentTime, bestTime = 10000;

    public Level10Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level10, container, false);

        b_main = view.findViewById(R.id.b_main);
        b_next_game = view.findViewById(R.id.b_next_game);

        b_main.setEnabled(true);

        startGame();

        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endTime = System.currentTimeMillis();
                currentTime = endTime - startTime;

                b_main.setText(currentTime + " ms");
                b_main.setEnabled(false);
                // Add this block to show the next game button if currentTime is 500
                if (currentTime < 500) {
                    b_next_game.setVisibility(View.VISIBLE);
                    b_next_game.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dbHelper.addScore(User.getUID(),50);
                            // Add the code to navigate to the next game here
                            FragmentManager fragmentManager = getParentFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            HomeFragment homeFragment = new HomeFragment();
                            fragmentTransaction.replace(R.id.flFragment, homeFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                    });
                } else {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    }, 2000);
                }
            }
        });

        return view;
    }

    private void startGame() {
        b_main.setText("");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                b_main.setBackgroundColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.blue));
                b_main.setText("PRESS");
                b_main.setEnabled(true);
            }
        }, 2000);
    }
}