package com.example.sehs4681_gp_grp2.Eric;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehs4681_gp_grp2.DBHelper;
import com.example.sehs4681_gp_grp2.Eric.L4GameClass.*;
import com.example.sehs4681_gp_grp2.Model.User;
import com.example.sehs4681_gp_grp2.R;

public class Level4Fragment extends Fragment implements OnCellClickListener{

    private DBHelper dbHelper;
    public static final long TIMER_LENGTH = 999000L;    // 999 seconds in milliseconds
    public static final int BOMB_COUNT = 10;
    public static final int GRID_SIZE = 10;

    private MineGridRecyclerAdapter mineGridRecyclerAdapter;
    private RecyclerView grid;
    private TextView smiley, timer, flag, flagsLeft;
    private MineSweeperGame mineSweeperGame;
    private CountDownTimer countDownTimer;
    private int secondsElapsed;
    private boolean timerStarted;

    public Level4Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_level4, container, false);
        grid = view.findViewById(R.id.activity_main_grid);
        timer = view.findViewById(R.id.activity_main_timer);
        flagsLeft = view.findViewById(R.id.activity_main_flagsleft);
        smiley = view.findViewById(R.id.activity_main_smiley);
        flag = view.findViewById(R.id.activity_main_flag);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        grid.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 10));

        timerStarted = false;
        countDownTimer = new CountDownTimer(TIMER_LENGTH, 1000) {
            public void onTick(long millisUntilFinished) {
                secondsElapsed += 1;
                timer.setText(String.format("%03d", secondsElapsed));
            }

            public void onFinish() {
                mineSweeperGame.outOfTime();
                Toast.makeText(getActivity().getApplicationContext(), "Game Over: Timer Expired", Toast.LENGTH_SHORT).show();
                mineSweeperGame.getMineGrid().revealAllBombs();
                mineGridRecyclerAdapter.setCells(mineSweeperGame.getMineGrid().getCells());
            }
        };


        mineSweeperGame = new MineSweeperGame(GRID_SIZE, BOMB_COUNT);
        flagsLeft.setText(String.format("%03d", mineSweeperGame.getNumberBombs() - mineSweeperGame.getFlagCount()));
        mineGridRecyclerAdapter = new MineGridRecyclerAdapter(mineSweeperGame.getMineGrid().getCells(), this);
        grid.setAdapter(mineGridRecyclerAdapter);

        smiley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mineSweeperGame = new MineSweeperGame(GRID_SIZE, BOMB_COUNT);
                mineGridRecyclerAdapter.setCells(mineSweeperGame.getMineGrid().getCells());
                timerStarted = false;
                countDownTimer.cancel();
                secondsElapsed = 0;
                timer.setText(R.string.lvl4_default_count);
                flagsLeft.setText(String.format("%03d", mineSweeperGame.getNumberBombs() - mineSweeperGame.getFlagCount()));
            }
        });

        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mineSweeperGame.toggleMode();
                if (mineSweeperGame.isFlagMode()) {
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(0xFFFFFFFF);
                    border.setStroke(1, 0xFF000000);
                    flag.setBackground(border);
                } else {
                    GradientDrawable border = new GradientDrawable();
                    border.setColor(0xFFFFFFFF);
                    flag.setBackground(border);
                }
            }
        });

    }

    @Override
    public void cellClick(Cell cell) {
        mineSweeperGame.handleCellClick(cell);

        flagsLeft.setText(String.format("%03d", mineSweeperGame.getNumberBombs() - mineSweeperGame.getFlagCount()));

        if (!timerStarted) {
            countDownTimer.start();
            timerStarted = true;
        }

        if (mineSweeperGame.isGameOver()) {
            countDownTimer.cancel();
            Toast.makeText(getActivity().getApplicationContext(), "Game Over", Toast.LENGTH_SHORT).show();
            vibrate();
            mineSweeperGame.getMineGrid().revealAllBombs();
        }

        if (mineSweeperGame.isGameWon()) {
            countDownTimer.cancel();
            Toast.makeText(getActivity().getApplicationContext(), "Game Won", Toast.LENGTH_SHORT).show();
            dbHelper.addScore(User.getUID(),50);
            mineSweeperGame.getMineGrid().revealAllBombs();
        }

        mineGridRecyclerAdapter.setCells(mineSweeperGame.getMineGrid().getCells());
    }

    private void vibrate () {
        final Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        final VibrationEffect vibrationEffect;

        // this is the only type of the vibration which requires system version Oreo (API 26)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // this effect creates the vibration of default amplitude for 1000ms(1 sec)
            vibrationEffect = VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE);

            // it is safe to cancel other vibrations currently taking place
            vibrator.cancel();
            vibrator.vibrate(vibrationEffect);
        }
    }
}