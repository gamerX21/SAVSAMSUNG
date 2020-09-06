package tn.krh.savsamsung.fragments.service;


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
import tn.krh.savsamsung.Adapters.service.serviceItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.services;
import tn.krh.savsamsung.retrofit.INodeJS;

/**
 * A simple {@link Fragment} subclass.
 */
public class Service_fragment extends Fragment {

    Context mContext;
    INodeJS myApi;
    @SerializedName("serviceList")
    @Expose
    List<services> serviceList = new ArrayList<>();
    RecyclerView serviceRV;
    public Service_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_fragment, container, false);
        //get data
        String adr = getString(R.string.remote_api_adr_phone);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<List<services>> call = myApi.getAllServices();
        call.enqueue(new Callback<List<services>>() {
            @Override
            public void onResponse(Call<List<services>> call, Response<List<services>> response) {
                serviceList.clear();
                for(services item:response.body()){

                    serviceList.add(item);
                    serviceItemsAdapter servicesAdapter = new serviceItemsAdapter(mContext,serviceList);
                    serviceRV = view.findViewById(R.id.ServiceItemsRVList);
                    serviceRV.setHasFixedSize(true);
                    serviceRV.setAdapter(servicesAdapter);
                    serviceRV.setLayoutManager(new LinearLayoutManager(mContext));
                    //helpsAdapter.notifyDataSetChanged();
                }
                if(response.message().equals("nothing was found")){
                    Toast.makeText(getActivity(),"aucun service disponible",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<services>> call, Throwable t) {

            }
        });
        return  view;
    }

}
