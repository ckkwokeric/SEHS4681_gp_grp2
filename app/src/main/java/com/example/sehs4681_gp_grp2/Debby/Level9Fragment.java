package com.example.sehs4681_gp_grp2.Debby;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehs4681_gp_grp2.DBHelper;
import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.Model.User;
import com.example.sehs4681_gp_grp2.R;

import java.util.Random;

public class Level9Fragment extends Fragment {

    private DBHelper dbHelper;
    ImageView iv_button, iv_arrow;
    TextView tv_points;
    ProgressBar progressBar;
    Handler handler;
    Runnable runnable;
    Button btn_next_game;

    private final static int State_Blue = 1;
    private final static int State_Purple = 2;
    private final static int State_Orange = 3;
    private final static int State_Green = 4;
    int buttonState = State_Blue;
    int arrowState = State_Blue;
    int currentTime = 4000;
    int startTime = 4000;
    int currentPoints = 0;

    public Level9Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_level9, container, false);

        iv_button = view.findViewById(R.id.iv_button);
        iv_arrow = view.findViewById(R.id.iv_arrow);
        tv_points = view.findViewById(R.id.tv_points);
        progressBar = view.findViewById(R.id.progressBar);
        btn_next_game = view.findViewById(R.id.btn_next_game);


        //set the initial progressbar time to 4 seconds
        progressBar.setMax(startTime);
        progressBar.setProgress(startTime);

        //display the starting points
        tv_points.setText("Point: " + currentPoints);

        //generate random arrow color at the start of the game
        class Random {
            public int nextInt(int i) {
                int min = 1; // Minimum value of range
                int max = 4; // Maximum value of range
                // Generate random int value from min to max
                int Random = (int)Math.floor(Math.random() * (max - min + 1) + min);

                return Random;
            }
        }

        Random r = new Random();
        //arrowState = r.nextInt(4) + 1;
        setArrowImage(arrowState);

        iv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotate the button with the colors
                setButtonImage(setButtonPosition(buttonState));
            }
        });

        btn_next_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addScore(User.getUID(),50);
                // Add your logic to navigate to the next game
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.flFragment, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        //main game loop
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                //show progress
                currentTime = currentTime - 100;
                progressBar.setProgress(currentTime);

                //check if there is still some time left in the progressbar
                if (currentTime > 0) {
                    handler.postDelayed(runnable, 100);
                } else {
                    //check if the colors of the arrow the button are the same
                    if (buttonState == arrowState) {
                        //increase and show the points
                        currentPoints = currentPoints + 1;
                        tv_points.setText("Points: " + currentPoints);

                        if (currentPoints >= 5) {
                            btn_next_game.setVisibility(View.VISIBLE);
                            Toast.makeText(getContext(), "Congratulations! You reached 5 points!", Toast.LENGTH_SHORT).show();
                            handler.removeCallbacks(runnable); // Stop the game loop
                        } else {
                            //make the speed higher after every turn / if the speed is 1 second, make it again 2 seconds
                            startTime = startTime - 100;
                            if (startTime < 1000) {
                                startTime = 2000;
                            }
                            progressBar.setMax(startTime);
                            currentTime = startTime;
                            progressBar.setProgress(currentTime);

                            //generate new color of the arrow
                            arrowState = r.nextInt(4) + 1;
                            setArrowImage(arrowState);

                            handler.postDelayed(runnable, 100);
                        }
                    } else {
                        Toast.makeText(getContext(), "Game Over!", Toast.LENGTH_SHORT).show();
                        resetGame();
                    }
                }
            }
        };

        //start the game loop
        handler.postDelayed(runnable, 100);

        return view;
    }

    private void resetGame() {
        Random r = new Random();
        // Reset all game variables
        currentPoints = 0;
        startTime = 4000;
        currentTime = startTime;
        progressBar.setMax(startTime);
        progressBar.setProgress(currentTime);
        tv_points.setText("Points: " + currentPoints);
        arrowState = r.nextInt(4) + 1;
        setArrowImage(arrowState);

        // Start the game loop again
        handler.postDelayed(runnable, 100);
    }

    //display the arrow color according to the generated number
    private void setArrowImage(int state) {
        int previousState = arrowState;
        Random r = new Random();
        do {
            state = r.nextInt(4) + 1;
        } while (state == previousState);
        arrowState = state;

        switch (state) {
            case State_Blue:
                iv_arrow.setImageResource(R.drawable.blue);
                break;
            case State_Green:
                iv_arrow.setImageResource(R.drawable.green);
                break;
            case State_Orange:
                iv_arrow.setImageResource(R.drawable.orange);
                break;
            case State_Purple:
                iv_arrow.setImageResource(R.drawable.purple);
                break;
        }
    }

    //rotate animation of the button when clicked
    private void setRotation(final ImageView image, final int drawable) {
        //rotate 90 degrees
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(100);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                image.setImageResource(drawable);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        image.startAnimation(rotateAnimation);
    }

    //set button colors position 1-4
    private int setButtonPosition(int position) {
        position = position + 1;
        if (position == 5) {
            position = 1;
        }
        return position;
    }

    //display the button colors positions
    private void setButtonImage(int state) {
        switch (state) {
            case State_Blue:
                setRotation(iv_button, R.drawable.choose_blue);
                buttonState = State_Blue;
                break;
            case State_Green:
                setRotation(iv_button, R.drawable.choose_green);
                buttonState = State_Green;
                break;
            case State_Orange:
                setRotation(iv_button, R.drawable.choose_orange);
                buttonState = State_Orange;
                break;
            case State_Purple:
                setRotation(iv_button, R.drawable.choose_purple);
                buttonState = State_Purple;
                break;
        }
    }
}