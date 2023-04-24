package com.example.sehs4681_gp_grp2;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList users_id, users_name, users_score;

    CustomAdapter(Context context, ArrayList users_id, ArrayList users_name, ArrayList users_score){
        this.context = context;
        this.users_id = users_id;
        this.users_name = users_name;
        this.users_score = users_score;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.rankText.setText(String.valueOf(position + 1) + ".");
//        holder.users_id_txt.setText(String.valueOf(users_id.get(position)));
        holder.users_name_txt.setText(String.valueOf(users_name.get(position)));
        holder.users_score_txt.setText(String.valueOf(users_score.get(position)));
    }

    @Override
    public int getItemCount() {
        return users_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rankText, users_id_txt, users_name_txt, users_score_txt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rankText = itemView.findViewById(R.id.rank_text);
//            users_id_txt = itemView.findViewById(R.id.user_id_txt);
            users_name_txt = itemView.findViewById(R.id.users_name_txt);
            users_score_txt = itemView.findViewById(R.id.users_score_txt);

        }
    }
}
