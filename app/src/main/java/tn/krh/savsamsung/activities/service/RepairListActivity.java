package tn.krh.savsamsung.activities.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.Adapters.service.RepairDemandItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.repairD;
import tn.krh.savsamsung.retrofit.INodeJS;

public class RepairListActivity extends AppCompatActivity {
    Context mContext;
    INodeJS myApi;
    @SerializedName("RepairList")
    @Expose
    List<repairD> repairList = new ArrayList<>();
    RecyclerView repairRV;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_list);
        mPreference = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //get data
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        int User_id= mPreference.getInt("IdUser",0);

        Call<List<repairD>> call = myApi.getAllRepairDForUserById(User_id);
        call.enqueue(new Callback<List<repairD>>() {
            @Override
            public void onResponse(Call<List<repairD>> call, Response<List<repairD>> response) {
                repairList.clear();
                for(repairD item:response.body()){

                    repairList.add(item);
                    RepairDemandItemsAdapter articlesAdapter = new RepairDemandItemsAdapter(mContext,repairList);
                    repairRV = findViewById(R.id.RepairDItemsRVList);
                    //articleRV.setHasFixedSize(true);
                    repairRV.setAdapter(articlesAdapter);
                    repairRV.setLayoutManager(new LinearLayoutManager(mContext));
                    //helpsAdapter.notifyDataSetChanged();
                }
                if(response.message().equals("nothing was found  for repairD for user")){
                    Toast.makeText(mContext,"aucune demande trouvée",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<repairD>> call, Throwable t) {

            }
        });

    }
}
