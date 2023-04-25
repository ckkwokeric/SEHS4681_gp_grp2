package com.example.sehs4681_gp_grp2.Connie;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehs4681_gp_grp2.Connie.L12GameClass.TicTacToeBoard;
import com.example.sehs4681_gp_grp2.DBHelper;
import com.example.sehs4681_gp_grp2.HomeActivity;
import com.example.sehs4681_gp_grp2.HomeFragment;
import com.example.sehs4681_gp_grp2.Model.User;
import com.example.sehs4681_gp_grp2.R;


public class Level12Fragment extends Fragment implements View.OnClickListener{

    private DBHelper dbHelper;
    private TicTacToeBoard ticTacToeBoard;
    Button playAgainBtn, homeBtn;
    TextView playerTurn;

    public Level12Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_level12, container, false);
        playAgainBtn = (Button) view.findViewById(R.id.play_again_btn);
        homeBtn = (Button) view.findViewById(R.id.home_btn);
        playerTurn = (TextView) view.findViewById(R.id.win_or_lose);
        ticTacToeBoard = view.findViewById(R.id.ticTacToeBoard);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Actual game logic
        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);

        ticTacToeBoard.setUpGame(playAgainBtn, homeBtn, playerTurn);

        playAgainBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.play_again_btn) {
            ticTacToeBoard.resetGame();
            ticTacToeBoard.invalidate();
        } else if (view.getId() == R.id.home_btn) {
            dbHelper.addScore(User.getUID(),50);
            Fragment toHomeFragment = new HomeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flFragment, toHomeFragment).commit();
        }
    }
}