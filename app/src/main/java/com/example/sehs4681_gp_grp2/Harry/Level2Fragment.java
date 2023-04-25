package com.example.sehs4681_gp_grp2.Harry;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehs4681_gp_grp2.DBHelper;
import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.Model.User;
import com.example.sehs4681_gp_grp2.R;

import java.util.Random;


public class Level2Fragment extends Fragment {

    private DBHelper dbHelper;
    private View circle1;
    private View circle2;
    private Button winButton;
    private TextView clickInstructions;
    private ConstraintLayout layout;
    private Handler handler;
    private Runnable moveCircles;
    private Random random;
    private boolean circle1Clicked;
    private boolean circle2Clicked;

    public Level2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level2, container, false);

        circle1 = view.findViewById(R.id.circle1);
        circle2 = view.findViewById(R.id.circle2);
        winButton = view.findViewById(R.id.win_button);
        clickInstructions = view.findViewById(R.id.click_instructions);
        layout = view.findViewById(R.id.main_layout);
        handler = new Handler();
        random = new Random();

        moveCircles = new Runnable() {
            @Override
            public void run() {
                moveCircle(circle1);
                moveCircle(circle2);
                circle1Clicked = false;
                circle2Clicked = false;
                handler.postDelayed(this, 800);
            }
        };

        circle1.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                circle1Clicked = true;
                checkForSuccess();
            }
            return true;
        });

        circle2.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                circle2Clicked = true;
                checkForSuccess();
            }
            return true;
        });

        winButton.setOnClickListener(v -> goBack());

        handler.postDelayed(moveCircles, 1000);

        return view;
    }

    private void moveCircle(View circle) {
        int x = random.nextInt(layout.getWidth() - circle.getWidth());
        int y = random.nextInt(layout.getHeight() -circle.getHeight());
        circle.setX(x);
        circle.setY(y);
    }

    private void checkForSuccess() {
        if (circle1Clicked && circle2Clicked) {
            winButton.setVisibility(View.VISIBLE);
            clickInstructions.setVisibility(View.GONE);
            handler.removeCallbacks(moveCircles);
        }
    }

    private void goBack() {
        circle1Clicked = false;
        circle2Clicked = false;
        dbHelper.addScore(User.getUID(),50);
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.flFragment, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}