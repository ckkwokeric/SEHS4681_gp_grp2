package com.example.sehs4681_gp_grp2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    private DBHelper dbHelper;
    private int userUid;

    private Button add_score_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(getContext());
        if (getArguments() != null) {
            userUid = getArguments().getInt("user_uid");
        }
    }

    public static AccountFragment newInstance(int uid) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putInt("user_uid", uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        add_score_button = view.findViewById(R.id.add_score_button);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        add_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.addScore(userUid,50);
            }
        });
    }
}