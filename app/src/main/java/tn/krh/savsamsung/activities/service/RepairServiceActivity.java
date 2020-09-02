package tn.krh.savsamsung.activities.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import tn.krh.savsamsung.R;

public class RepairServiceActivity extends AppCompatActivity {
    CardView repairD;
    CardView GarantieD;
    CardView ListRepaidD;
    Context context;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_service);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        int user_session= mPreference.getInt("UserSession",0);

        repairD = findViewById(R.id.repairD);
        GarantieD = findViewById(R.id.GarantieD);
        ListRepaidD = findViewById(R.id.ListRepaidD);
        repairD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_session == 0){
                    Toast.makeText(v.getContext(),"vous devez crée un compte pour ajouter une demande",Toast.LENGTH_LONG).show();

                }
                else {
                    startActivity(new Intent(RepairServiceActivity.this, AddDemandRepair.class));

                }
            }
        });
        GarantieD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String url = "https://www.toutsurmesfinances.com/argent/a/extension-de-garantie-et-electromenager-definition-fonctionnement-et-prix#:~:text=Selon%20les%20vendeurs%20et%20le,3%20ans%20d%27extension).";
               Intent i = new Intent(Intent.ACTION_VIEW);
               i.setData(Uri.parse(url));
               startActivity(i);
            }
        });
        ListRepaidD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if(user_session == 0){
                 Toast.makeText(v.getContext(),"vous devez crée un compte pour consulter votre liste de demande",Toast.LENGTH_LONG).show();
             }
             else{
                 startActivity(new Intent(RepairServiceActivity.this,RepairListActivity.class));
             }
            }
        });
    }
}
