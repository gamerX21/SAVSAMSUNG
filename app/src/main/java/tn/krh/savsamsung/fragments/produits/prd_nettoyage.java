package tn.krh.savsamsung.fragments.produits;


import android.content.Context;
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
import tn.krh.savsamsung.Adapters.produit.produitItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.produit;
import tn.krh.savsamsung.retrofit.INodeJS;

/**
 * A simple {@link Fragment} subclass.
 */
public class prd_nettoyage extends Fragment {
    Context mContext;
    INodeJS myApi;
    @SerializedName("NettoyagesList")
    @Expose
    List<produit> produitList = new ArrayList<>();
    RecyclerView PartsRV;

    public prd_nettoyage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prd_nettoyage, container, false);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        String type = "nettoyage";
        Call<List<produit>> call = myApi.getAllProduitsByType(type);
        call.enqueue(new Callback<List<produit>>() {
            @Override
            public void onResponse(Call<List<produit>> call, Response<List<produit>> response) {
                produitList.clear();
                for(produit item:response.body()){

                    produitList.add(item);
                    produitItemsAdapter produitAdapter = new produitItemsAdapter(mContext,produitList);
                    PartsRV = view.findViewById(R.id.NettoyageItemsRVList);
                    PartsRV.setHasFixedSize(true);
                    PartsRV.setAdapter(produitAdapter);
                    PartsRV.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,
                            false));
                    //helpsAdapter.notifyDataSetChanged();
                }
                if(response.message().equals("nothing was found")){
                    Toast.makeText(getActivity(),"aucun produit disponible",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<produit>> call, Throwable t) {

            }
        });
        return view;
    }

}
