package tn.krh.savsamsung.activities.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import tn.krh.savsamsung.Adapters.spinner.TypeSpinnerAdapter;
import tn.krh.savsamsung.Adapters.spinner.VilleSpinnerAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.SpinnerData.SpinnerItem;
import tn.krh.savsamsung.retrofit.INodeJS;
import tn.krh.savsamsung.retrofit.RetroClient;

public class AddDemandRepair extends AppCompatActivity {
    Spinner RepairTypeSpinner,RepairDemandVilleSpinner;
    EditText RepairPhone,RepairAdresse;
    Button AddRepairBtn;
    ArrayList<SpinnerItem> TypeList = new ArrayList<>();
    ArrayList<SpinnerItem> villeList = new ArrayList<>();
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    INodeJS myApi;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_demand_repair_activity);
        TypeList.add(new SpinnerItem("Select",R.drawable.wrench));
        TypeList.add(new SpinnerItem("Lave-linge",R.drawable.washingmachine));
        TypeList.add(new SpinnerItem("Lave-Séche",R.drawable.dryer));
        TypeList.add(new SpinnerItem("Lave-vaisselle",R.drawable.dishwasher));
        TypeList.add(new SpinnerItem("réfrigérateur",R.drawable.fridge));
        //set ville list in spinner
        villeList.add(new SpinnerItem("selectionner une ville"));
        villeList.add(new SpinnerItem("ariana"));
        villeList.add(new SpinnerItem("béja"));
        villeList.add(new SpinnerItem("ben arous"));
        villeList.add(new SpinnerItem("bizerte"));
        villeList.add(new SpinnerItem("gabes"));
        villeList.add(new SpinnerItem("gafsa"));
        villeList.add(new SpinnerItem("jendouba"));
        villeList.add(new SpinnerItem("kairouan"));
        villeList.add(new SpinnerItem("kasserine"));
        villeList.add(new SpinnerItem("kebili"));
        villeList.add(new SpinnerItem("manouba"));
        villeList.add(new SpinnerItem("kef"));
        villeList.add(new SpinnerItem("mahdia"));
        villeList.add(new SpinnerItem("médenine"));
        villeList.add(new SpinnerItem("monastir"));
        villeList.add(new SpinnerItem("nabeul"));
        villeList.add(new SpinnerItem("sfax"));
        villeList.add(new SpinnerItem("sidi bouzid"));
        villeList.add(new SpinnerItem("siliana"));
        villeList.add(new SpinnerItem("sousse"));
        villeList.add(new SpinnerItem("tataouine"));
        villeList.add(new SpinnerItem("tozeur"));
        villeList.add(new SpinnerItem("tunis"));
        villeList.add(new SpinnerItem("zaghouan"));




        TypeSpinnerAdapter RepairTypeAdapter = new TypeSpinnerAdapter(this,R.layout.spinner_layout,R.id.SpinnerTxt,TypeList);
        VilleSpinnerAdapter RepairVilleAdapter = new VilleSpinnerAdapter(this,R.layout.spinner_layout_name,R.id.SpinnerTxtVille,villeList);
        //set UI
        RepairTypeSpinner = findViewById(R.id.RepairDemandTypeSpinner);
        RepairDemandVilleSpinner = findViewById(R.id.RepairDemandVilleSpinner);
        RepairPhone = findViewById(R.id.AddRepairPhone);
        RepairAdresse = findViewById(R.id.AddRepairAdresse);
        AddRepairBtn = findViewById(R.id.AddRepairBtn);
        RepairTypeSpinner.setAdapter(RepairTypeAdapter);
        RepairDemandVilleSpinner.setAdapter(RepairVilleAdapter);
        RepairTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem SelectedType = (SpinnerItem)  parent.getItemAtPosition(position);
                String type = SelectedType.getName();
                if(position > 0){
                    mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreference.edit();
                    editor.putString("SelectedRepairType",type);
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RepairDemandVilleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerItem SelectedType = (SpinnerItem)  parent.getItemAtPosition(position);
                String ville = SelectedType.getName();
                if(position > 0){
                    mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
                    SharedPreferences.Editor editor = mPreference.edit();
                    editor.putString("SelectedRepairVille",ville);
                    editor.apply();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        AddRepairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RepairPhone.getText().toString().equals("") || RepairAdresse.getText().toString().equals("")){
                    Toast.makeText(AddDemandRepair.this,"saissir vos informations svp",Toast.LENGTH_SHORT).show();
                }
                else {
                    AddNewRepairDemand(mPreference.getInt("IdUser",0),mPreference.getString("SelectedRepairType",""),mPreference.getString("SelectedRepairVille",""),RepairAdresse.getText().toString(),RepairPhone.getText().toString());
                }
            }
        });
    }
    private void AddNewRepairDemand(int userId,String type,String ville,String adresse,String phone)
    {

        Random random = new Random();
        int max = 9000;
        int min = 1000;
        int generated_num = random.nextInt(max - min) + min;

        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        int phoneC = Integer.parseInt(phone);
        compositeDisposable.add(myApi.AddNewRepairDemand(userId,type,ville,adresse,generated_num,phoneC,"en cours")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
}
