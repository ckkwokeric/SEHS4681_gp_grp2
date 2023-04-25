package com.example.sehs4681_gp_grp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    private DBHelper dbHelper;
    private int userUid, userScore;
    private String userName;

    private Button add_score_button;
    private TextView tv_userName, tv_userScore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        userUid = getArguments().getInt("user_uid");
        userName = getArguments().getString("user_name");
        userScore = getArguments().getInt("user_score");
    }

    public static AccountFragment newInstance(int uid, String userName, int userScore) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt("user_uid", uid);
        args.putString("user_name", userName);
        args.putInt("user_score", userScore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        add_score_button = view.findViewById(R.id.add_score_button);
        tv_userName = view.findViewById(R.id.acc_frag_user_name);
        tv_userScore = view.findViewById(R.id.acc_frag_user_score);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_userName.setText(userName);
        tv_userScore.setText(String.format("Your current score: %d", userScore));

        add_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.addScore(userUid,50);
            }
        });
    }
}