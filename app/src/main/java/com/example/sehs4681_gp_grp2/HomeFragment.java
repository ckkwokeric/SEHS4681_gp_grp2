package com.example.sehs4681_gp_grp2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.sehs4681_gp_grp2.Connie.Level11Fragment;
import com.example.sehs4681_gp_grp2.Connie.Level12Fragment;
import com.example.sehs4681_gp_grp2.Debby.Level10Fragment;
import com.example.sehs4681_gp_grp2.Debby.Level9Fragment;
import com.example.sehs4681_gp_grp2.Eric.Level3Fragment;
import com.example.sehs4681_gp_grp2.Eric.Level4Fragment;
import com.example.sehs4681_gp_grp2.Harry.Level1Fragment;
import com.example.sehs4681_gp_grp2.Harry.Level2Fragment;
import com.example.sehs4681_gp_grp2.Jason.Level7Fragment;
import com.example.sehs4681_gp_grp2.Jason.Level8Fragment;
import com.example.sehs4681_gp_grp2.Mark.Level5Fragment;
import com.example.sehs4681_gp_grp2.Mark.Level6Fragment;

public class HomeFragment extends Fragment implements View.OnClickListener {

    public HomeFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        // Set the onClickListener for the button
        Button bt_lv1 = view.findViewById(R.id.bt_lv1);
        bt_lv1.setOnClickListener(this);
        Button bt_lv2 = view.findViewById(R.id.bt_lv2);
        bt_lv2.setOnClickListener(this);
        Button bt_lv3 = view.findViewById(R.id.bt_lv3);
        bt_lv3.setOnClickListener(this);
        Button bt_lv4 = view.findViewById(R.id.bt_lv4);
        bt_lv4.setOnClickListener(this);
        Button bt_lv5 = view.findViewById(R.id.bt_lv5);
        bt_lv5.setOnClickListener(this);
        Button bt_lv6 = view.findViewById(R.id.bt_lv6);
        bt_lv6.setOnClickListener(this);
        Button bt_lv7 = view.findViewById(R.id.bt_lv7);
        bt_lv7.setOnClickListener(this);
        Button bt_lv8 = view.findViewById(R.id.bt_lv8);
        bt_lv8.setOnClickListener(this);
        Button bt_lv9 = view.findViewById(R.id.bt_lv9);
        bt_lv9.setOnClickListener(this);
        Button bt_lv10 = view.findViewById(R.id.bt_lv10);
        bt_lv10.setOnClickListener(this);
        Button bt_lv11 = view.findViewById(R.id.bt_lv11);
        bt_lv11.setOnClickListener(this);
        Button bt_lv12 = view.findViewById(R.id.bt_lv12);
        bt_lv12.setOnClickListener(this);

        return view;
    }


    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (view.getId()){
            case R.id.bt_lv1:
                // Replace the current fragment with the new fragment
                Level1Fragment level1Fragment = new Level1Fragment();
                fragmentTransaction.replace(R.id.flFragment, level1Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv2:
                // Replace the current fragment with the new fragment
                Level2Fragment level2Fragment = new Level2Fragment();
                fragmentTransaction.replace(R.id.flFragment, level2Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv3:
                // Replace the current fragment with the new fragment
                Level3Fragment level3Fragment = new Level3Fragment();
                fragmentTransaction.replace(R.id.flFragment, level3Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv4:
                // Replace the current fragment with the new fragment
                Level4Fragment level4Fragment = new Level4Fragment();
                fragmentTransaction.replace(R.id.flFragment, level4Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv5:
                // Replace the current fragment with the new fragment
                Level5Fragment level5Fragment = new Level5Fragment();
                fragmentTransaction.replace(R.id.flFragment, level5Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv6:
                // Replace the current fragment with the new fragment
                Level6Fragment level6Fragment = new Level6Fragment();
                fragmentTransaction.replace(R.id.flFragment, level6Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv7:
                // Replace the current fragment with the new fragment
                Level7Fragment level7Fragment = new Level7Fragment();
                fragmentTransaction.replace(R.id.flFragment, level7Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv8:
                // Replace the current fragment with the new fragment
                Level8Fragment level8Fragment = new Level8Fragment();
                fragmentTransaction.replace(R.id.flFragment, level8Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv9:
                // Replace the current fragment with the new fragment
                Level9Fragment level9Fragment = new Level9Fragment();
                fragmentTransaction.replace(R.id.flFragment, level9Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv10:
                // Replace the current fragment with the new fragment
                Level10Fragment level10Fragment = new Level10Fragment();
                fragmentTransaction.replace(R.id.flFragment, level10Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv11:
                // Replace the current fragment with the new fragment
                Level11Fragment level11Fragment = new Level11Fragment();
                fragmentTransaction.replace(R.id.flFragment, level11Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.bt_lv12:
                // Replace the current fragment with the new fragment
                Level12Fragment level12Fragment = new Level12Fragment();
                fragmentTransaction.replace(R.id.flFragment, level12Fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }

    }
}