package tn.krh.savsamsung.activities.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.article;
import tn.krh.savsamsung.entity.produit;
import tn.krh.savsamsung.retrofit.INodeJS;

public class DetailsStoreItem extends AppCompatActivity {
    INodeJS myApi;
    TextView nom,Description,LLp,prix;
    Button CommanderBtn,DispoBtn;
    ImageView img;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_store_item);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        int ProduitId = mPreference.getInt("ProduitId",0);
        //initialize view
        nom = findViewById(R.id.detailsProduitNom);
        Description = findViewById(R.id.detailsProduitDesc);
        LLp = findViewById(R.id.detailsProduitLL);
        prix = findViewById(R.id.detailsProduitPrix);
        DispoBtn = findViewById(R.id.DispoBtn);
        CommanderBtn = findViewById(R.id.CommanderDetailsBtn);
        img = findViewById(R.id.detailsProduitImg);

        //set data to details
        setProduitData(ProduitId);

    }

    private void setProduitData(int produitId) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<produit> call = myApi.getProduitById(produitId);
        call.enqueue(new Callback<produit>() {
            @Override
            public void onResponse(Call<produit> call, Response<produit> response) {
                produit item = response.body();
                nom.setText(item.getNom());
                Description.setText(item.getDescription());
                String Lc1 = String.valueOf(item.getLargeur());
                String lc2 = String.valueOf(item.getLongueur());
                String prixC = String.valueOf(item.getPrix());
                prix.setText(prixC + " DT");
                int stock = item.getStock();
                if(stock > 0)
                {
                    DispoBtn.setText("disponible");
                    CommanderBtn.setEnabled(true);
                    DispoBtn.setBackgroundColor(getResources().getColor(R.color.dispo_color));

                }
                else{
                    DispoBtn.setText("hors stock");
                    CommanderBtn.setEnabled(false);
                    DispoBtn.setBackgroundColor(getResources().getColor(R.color.outS_color));

                }
                LLp.setText("Dimensions: " + Lc1 + " cm " +"*" + lc2 + " cm");
                //get Image
                String ProduitItem_img = item.getImg();
                //load Img

                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:3000")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                myApi = retrofit.create(INodeJS.class);
                Call<ResponseBody> callImg = myApi.getHelpItemImg(ProduitItem_img);
                callImg.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> calll, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                // display the image data in a ImageView or save it
                                Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                img.setImageBitmap(bmp);
                            } else {
                                // TODO
                            }
                        } else {
                            // TODO
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> calll, Throwable t) {
                        // TODO
                    }
                });
            }
            @Override
            public void onFailure(Call<produit> call, Throwable t) {

            }
        });
    }
}
