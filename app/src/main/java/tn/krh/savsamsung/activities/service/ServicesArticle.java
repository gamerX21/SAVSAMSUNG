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
import tn.krh.savsamsung.Adapters.service.articleItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.article;
import tn.krh.savsamsung.retrofit.INodeJS;

public class ServicesArticle extends AppCompatActivity {
    Context mContext;
    INodeJS myApi;
    @SerializedName("articleList")
    @Expose
    List<article> articleList = new ArrayList<>();
    RecyclerView articleRV;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_article);
        mPreference = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        //get data
        String adr = getString(R.string.remote_api_adr_phone);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        String article_service_type= mPreference.getString("ServiceType","");

        Call<List<article>> call = myApi.getAllArticlesByServiceType(article_service_type);
        call.enqueue(new Callback<List<article>>() {
            @Override
            public void onResponse(Call<List<article>> call, Response<List<article>> response) {
                articleList.clear();
                for(article item:response.body()){

                    articleList.add(item);
                    articleItemsAdapter articlesAdapter = new articleItemsAdapter(mContext,articleList);
                    articleRV = findViewById(R.id.ArticleItemsRVListAct);
                    //articleRV.setHasFixedSize(true);
                    articleRV.setAdapter(articlesAdapter);
                    articleRV.setLayoutManager(new LinearLayoutManager(mContext));
                    //helpsAdapter.notifyDataSetChanged();
                }
                if(response.message().equals("nothing was found")){
                    Toast.makeText(mContext,"aucun article pour ce service",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<article>> call, Throwable t) {

            }
        });
    }


}
