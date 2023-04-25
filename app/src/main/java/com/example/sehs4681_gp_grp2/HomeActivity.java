package com.example.sehs4681_gp_grp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.sehs4681_gp_grp2.Login_Registration.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, LogoutDialog.NoticeDialogListener {


    BottomNavigationView bottomNavigationView;

    //        HomeFragment homeFragment = new HomeFragment();
//        AccountFragment accountFragment = new AccountFragment();
//        ScoreboardFragment scoreboardFragment = new ScoreboardFragment();
    private int userUid;
    private String userName;
    private int userScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        Intent intent = getIntent();
        userUid = intent.getIntExtra("UID", -1);
        userName = intent.getStringExtra("userName");
        userScore = intent.getIntExtra("userScore", -1);

    }

    public boolean onNavigationItemSelected(MenuItem item){
        displayView(item.getItemId());
        return true;
    }

    public void displayView(int viewId) {

        Fragment fragment = null;

        switch (viewId) {
            case R.id.person:
                fragment = AccountFragment.newInstance(userUid, userName, userScore);
                break;
            case R.id.home:
                fragment = new HomeFragment();
                break;
            case R.id.scoreboard:
                fragment = new ScoreboardFragment();
                break;
            case R.id.logout:
                LogoutDialog dialog = new LogoutDialog();
                dialog.show(getSupportFragmentManager(),"LogoutDialog");
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flFragment, fragment);
            ft.commit();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void backToRegistrationPage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    * Below two functions are from LogoutDialog.NoticeDialogListener that implemented at the top of this class
    *
    * onDialogPositiveClick --> When user click "OK"/"Yes", the app returns to Login page
    * onDialogNegativeClick --> When user click "Cancel"/"No", the app remains at the same page and do nothing
    *
    * */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // TODO Except returning to MainActivity, might be need to clear some credential of login
        backToRegistrationPage();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing...
    }
}
