package com.example.sehs4681_gp_grp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AccountFragment extends Fragment {

    private DBHelper dbHelper;

    public AccountFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        dbHelper = new DBHelper(getContext());

        Button add_score_button = view.findViewById(R.id.add_score_button);
        add_score_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.addScore(1,50);
            }
        });

        return view;
    }

}