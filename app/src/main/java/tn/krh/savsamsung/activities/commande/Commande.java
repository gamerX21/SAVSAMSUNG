package tn.krh.savsamsung.activities.commande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.profile.ProfileActivity;
import tn.krh.savsamsung.database.AppDataBase;
import tn.krh.savsamsung.entity.produit;
import tn.krh.savsamsung.entity.shoppingCart;
import tn.krh.savsamsung.entity.user;
import tn.krh.savsamsung.retrofit.INodeJS;
import tn.krh.savsamsung.retrofit.RetroClient;

public class Commande extends AppCompatActivity {
    TextView refCmd,NomProduit,PrixProduit,PrixTT;
    Button ConfirmerCmdBtn,AddItemToShoppingCartBtn;
    NumberPicker QuantiteNumberPicker;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    private AppDataBase database;

    Context mContext;
    INodeJS myApi;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commande);
        refCmd = findViewById(R.id.CommandeRef);
        NomProduit = findViewById(R.id.NOMProduitCMD);
        PrixProduit = findViewById(R.id.PrixProduitCMD);
        QuantiteNumberPicker = findViewById(R.id.QuantiteNumberPicker);
        PrixTT = findViewById(R.id.PrixQuanCMD);
        ConfirmerCmdBtn = findViewById(R.id.ConfirmerCMDBtn);
        AddItemToShoppingCartBtn = findViewById(R.id.AddItemToShoppingCartBtn);
        //set RANDOM REF for CMD
        Random random = new Random();
        int max = 10000;
        int min = 0001;
        int generated_num = random.nextInt(max - min) + min;
        String ref = String.valueOf(generated_num);
        refCmd.setText("Commande Ref: " +ref);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        int produit_id = mPreference.getInt("ProduitId",0);
        int user_id = mPreference.getInt("IdUser",0);
        //set commande data from store clicked item
        setCommandeData(produit_id);
        int prixPrd = mPreference.getInt("prixPrd",0);
        PrixTT.setText(prixPrd + "DT");
        int quan = 0;
        QuantiteNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int TT = newVal * prixPrd ;
                String prixS = String.valueOf(TT);
                PrixTT.setText(prixS + "DT");
            }
        });

        AddItemToShoppingCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int TT = (QuantiteNumberPicker.getValue() * prixPrd);

                AddToCart(user_id,produit_id,QuantiteNumberPicker.getValue(),TT);
            }
        });



        ConfirmerCmdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCommand(generated_num,user_id,produit_id,QuantiteNumberPicker.getValue());
            }
        });

    }

    private void AddToCart(int user_id, int produit_id, int quan,int TT) {
        database = AppDataBase.getAppDatabase(this);
        shoppingCart shoppingItem = new shoppingCart(user_id,produit_id,quan,TT);
        database.userDao().insertUserShoppingCartItem(shoppingItem);
        Toast.makeText(this,"produit ajoutée avec succés",Toast.LENGTH_SHORT).show();
    }

    private void AddNewCommand(int ref,int user_id,int produit_id,int quan)
    {
        Retrofit retrofit = RetroClient.getInstance();
        myApi = retrofit.create(INodeJS.class);
        compositeDisposable.add(myApi.AddNewCommand(user_id,ref,produit_id,quan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                    }
                })
        );
    }
    private void setCommandeData(int produit_id) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<produit> call = myApi.getProduitById(produit_id);
        call.enqueue(new Callback<produit>() {
            @Override
            public void onResponse(Call<produit> call, Response<produit> response) {
                produit prd = response.body();
                NomProduit.setText(prd.getNom());
                String prixC = String.valueOf(prd.getPrix());
                PrixProduit.setText(prixC + " DT");
                QuantiteNumberPicker.setMinValue(1);
                QuantiteNumberPicker.setMaxValue(prd.getStock()-1);
                SharedPreferences.Editor editor = mPreference.edit();
                editor.putInt("prixPrd",prd.getPrix());
                editor.apply();
            }

            @Override
            public void onFailure(Call<produit> call, Throwable t) {

            }
        });
    }
}
