package tn.krh.savsamsung.activities.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.profile.ProfileActivity;
import tn.krh.savsamsung.fragments.service.Service_fragment;
import tn.krh.savsamsung.fragments.signin.SignInFrag;
import tn.krh.savsamsung.fragments.store.Store;
import tn.krh.savsamsung.fragments.help.fragment_help_items_list;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private BottomNavigationView MainBottomNavMenu;
    private FrameLayout FrameLayoutSAV;
    private Service_fragment service_fragment;
    private fragment_help_items_list helpFragment;
    private Store store;
    private SignInFrag signInFrag;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //MAIN NAVIGATION
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        int user_session= mPreference.getInt("UserSession",0);
        MainBottomNavMenu = findViewById(R.id.main_nav);
        //set fragments
        signInFrag = new SignInFrag();
        service_fragment = new Service_fragment();
        store = new Store();
        helpFragment = new fragment_help_items_list();
        setFragment(helpFragment);
        //set layout frame layout
        FrameLayoutSAV = findViewById(R.id.FrameLayoutSAV);
        //Bottom Menu navigation listener
        MainBottomNavMenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.main :
                        setFragment(helpFragment);
                        return true;
                    case R.id.repair :
                        setFragment(service_fragment);
                        return true;
                    case R.id.profile :
                        if(user_session == 0){
                            setFragment(signInFrag);
                        }
                        else startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        return true;
                    case R.id.shop :
                        setFragment(store);
                        return true;
                    default:
                        return  false;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            Toast.makeText(getApplication(),"appuyer 2 fois pour fermer l'application",Toast.LENGTH_LONG).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FrameLayoutSAV, fragment).commit();
    }
}
