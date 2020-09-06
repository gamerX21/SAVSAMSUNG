package tn.krh.savsamsung.activities.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.main.MainActivity;
import tn.krh.savsamsung.activities.service.RepairListActivity;
import tn.krh.savsamsung.entity.user;
import tn.krh.savsamsung.retrofit.INodeJS;

public class ProfileActivity extends AppCompatActivity  {
    private long backPressedTime;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    Context mContext;
    INodeJS myApi;
    //implements GoogleApiClient.OnConnectionFailedListener
    TextView name,email,tel,adresse;
    CardView repairDProfil;
    Button SignOutBtn;
    /*
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = findViewById(R.id.NPprofil);
        email = findViewById(R.id.Emailprofil);
        tel = findViewById(R.id.Telprofil);
        adresse = findViewById(R.id.Adrprofil);
        SignOutBtn = findViewById(R.id.SignOutBtn);
        repairDProfil = findViewById(R.id.repairDProfil);
        mPreference = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        String userEmail= mPreference.getString("emailUser","");
        SignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mPreference.edit();
                editor.putInt("UserSession",0);
                editor.apply();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });
        repairDProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, RepairListActivity.class));
            }
        });
         /*
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API ,gso)
                .build();

          */
        setProfileData(userEmail);
    }

    private void setProfileData(String user_email) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<user> call = myApi.getUserDataByEmail(user_email);
        call.enqueue(new Callback<user>() {
            @Override
            public void onResponse(Call<user> call, Response<user> response) {
                user usr = response.body();
                String phone = String.valueOf(usr.getTel());
                name.setText(usr.getFullname());
                email.setText(usr.getEmail());
                tel.setText(phone);
                adresse.setText(usr.getAdresse());
                SharedPreferences.Editor editor = mPreference.edit();
                editor.putInt("IdUser",usr.getId());
                editor.apply();
            }

            @Override
            public void onFailure(Call<user> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "" +t, Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            return;
        }else {
            Toast.makeText(getApplication(),"appuyer 2 fois",Toast.LENGTH_LONG).show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    /*
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            name.setText(account.getDisplayName());
            email.setText(account.getEmail());
            id.setText(account.getId());
            Picasso.get().load(account.getPhotoUrl()).placeholder(R.mipmap.ic_launcher).into(profileImg);
        }else {
            startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else {
             opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                 @Override
                 public void onResult(@NonNull GoogleSignInResult result) {
                     handleSignInResult(result);
                 }
             });
        }
    }

     */
}
