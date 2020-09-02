package tn.krh.savsamsung.activities.article;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.article;
import tn.krh.savsamsung.fragments.service.ServiceAstucesFragment;
import tn.krh.savsamsung.retrofit.INodeJS;

public class ArticleDetails extends AppCompatActivity {
    INodeJS myApi;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    TextView titre,description;
    FrameLayout showAstuceForArticle;
    ServiceAstucesFragment serviceAstucesFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);
        mPreference = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        titre = findViewById(R.id.aideTitre);
        description = findViewById(R.id.aideDesc);
        showAstuceForArticle = findViewById(R.id.showAstuceForArticle);
        serviceAstucesFragment = new ServiceAstucesFragment();
        int article_id= mPreference.getInt("ArticleId",0);
        setData(article_id);
    }
    private void setData(int article_id) {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<article> call = myApi.getArticleById(article_id);
        call.enqueue(new Callback<article>() {
            @Override
            public void onResponse(Call<article> call, Response<article> response) {
                article item = response.body();
                titre.setText(item.getTitre());
                description.setText(item.getDescription());
                setFragment(serviceAstucesFragment);
            }

            @Override
            public void onFailure(Call<article> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"get  request for article details failed",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.showAstuceForArticle, fragment).commit();
    }
}
