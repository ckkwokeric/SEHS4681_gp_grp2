package com.example.sehs4681_gp_grp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.sehs4681_gp_grp2.Login_Registration.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, LogoutDialog.NoticeDialogListener {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    AccountFragment accountFragment = new AccountFragment();
    ScoreboardFragment scoreboardFragment = new ScoreboardFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    public boolean onNavigationItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.person:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, accountFragment).commit();
                return true;

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                return true;

            case R.id.scoreboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, scoreboardFragment).commit();
                return true;

            case R.id.logout:
                LogoutDialog dialog = new LogoutDialog();
                dialog.show(getSupportFragmentManager(),"LogoutDialog");
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void mainactivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /*
    * Below two functions are from LogoutDialog.NoticeDialogListener that implemented at the top of this class
    *
    * onDialogPositiveClick --> When user click "OK"/"Yes", the app returns to Login page
    * onDialogNegativeClick --> When user clici "Cancel"/"No", the app remains at the same page and do nothing
    *
    * */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        // TODO Except returning to MainActivity, might be need to clear some credential of login
        mainactivity();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // Do nothing...
    }
}
