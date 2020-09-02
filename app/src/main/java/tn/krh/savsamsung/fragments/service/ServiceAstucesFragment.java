package tn.krh.savsamsung.fragments.service;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import tn.krh.savsamsung.Adapters.astuceItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.astuce;
import tn.krh.savsamsung.retrofit.INodeJS;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceAstucesFragment extends Fragment {

    Context mContext;
    INodeJS myApi;
    @SerializedName("astuceList")
    @Expose
    List<astuce> astuceList = new ArrayList<>();
    RecyclerView astuceRV;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
    public ServiceAstucesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_astuces, container, false);
        mPreference = getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);

        //get data
        String adr = getString(R.string.remote_api_adr_phone);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        int article_id= mPreference.getInt("ArticleId",0);

        Call<List<astuce>> call = myApi.getAllAstucesByArticleId(article_id);
        call.enqueue(new Callback<List<astuce>>() {
            @Override
            public void onResponse(Call<List<astuce>> call, Response<List<astuce>> response) {
                astuceList.clear();
                for(astuce item:response.body()){

                    astuceList.add(item);
                    astuceItemsAdapter astucesAdapter = new astuceItemsAdapter(mContext,astuceList);
                    astuceRV = view.findViewById(R.id.AstuceItemsRVList);
                    astuceRV.setHasFixedSize(true);
                    astuceRV.setAdapter(astucesAdapter);
                    astuceRV.setLayoutManager(new LinearLayoutManager(mContext));
                    //helpsAdapter.notifyDataSetChanged();
                }
                if(response.message().equals("nothing was found")){
                    Toast.makeText(getActivity(),"aucun astuce pour cet article",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<astuce>> call, Throwable t) {

            }
        });
        return  view;
    }

}
