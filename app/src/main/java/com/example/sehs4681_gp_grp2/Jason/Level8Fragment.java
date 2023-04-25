package com.example.sehs4681_gp_grp2.Jason;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehs4681_gp_grp2.DBHelper;
import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.Model.User;
import com.example.sehs4681_gp_grp2.R;

public class Level8Fragment extends Fragment {

    private DBHelper dbHelper;
    private Button btn, btn_restart;

    public Level8Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_level8, container, false);

        CustomGameView customGameView = view.findViewById(R.id.customGameView);
        btn = view.findViewById(R.id.btn_next2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.addScore(User.getUID(),50);
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.flFragment, homeFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_restart = view.findViewById(R.id.btn_restart);
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartFragment();
            }
        });

        customGameView.setOnGameWonListener(() -> {
            Toast.makeText(getActivity(), "Congratulations! You won!", Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
        });

        customGameView.setOnGameLostListener(() -> {
            Toast.makeText(getActivity(), "Times up! Please try again!", Toast.LENGTH_SHORT).show();
            btn_restart.setVisibility(View.VISIBLE);
        });

        customGameView.startTimer();

        return view;
    }

    private void restartFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Level8Fragment level8Fragment = new Level8Fragment();
        fragmentTransaction.replace(R.id.flFragment, level8Fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}