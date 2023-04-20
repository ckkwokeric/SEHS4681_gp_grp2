package com.example.sehs4681_gp_grp2.Jason;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.R;

public class Level8Fragment extends Fragment implements View.OnClickListener {

    private Button btn;

    public Level8Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level8, container, false);

        CustomGameView customGameView = view.findViewById(R.id.customGameView);
        TextView gameStatusTextView = view.findViewById(R.id.gameStatusTextView);
        btn = view.findViewById(R.id.btn_next2);
        btn.setOnClickListener(this);

        customGameView.setOnGameWonListener(() -> {
            gameStatusTextView.setText("Congratulations! You won the game!");
            gameStatusTextView.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
        });

        customGameView.setOnGameLostListener(() -> {
            gameStatusTextView.setText("Time's up! You lost the game.");
            gameStatusTextView.setVisibility(View.VISIBLE);
        });

        customGameView.startTimer();

        return view;
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