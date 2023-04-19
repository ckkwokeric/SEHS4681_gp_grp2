package com.example.sehs4681_gp_grp2;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ScoreboardFragment extends Fragment {

    RecyclerView recyclerView;
    DBHelper db;
    ArrayList<String> users_id, users_name, users_score;
    CustomAdapter customAdapter;

    public ScoreboardFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        db = new DBHelper(getActivity());
        users_id = new ArrayList<>();
        users_name = new ArrayList<>();
        users_score = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(getActivity(), users_id, users_name, users_score);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void storeDataInArrays() {
        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getActivity(),"No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                users_id.add(cursor.getString(0));
                users_name.add(cursor.getString(1));
                users_score.add(cursor.getString(3));
            }
        }
    }
}