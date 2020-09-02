package tn.krh.savsamsung.fragments.help;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import tn.krh.savsamsung.Adapters.helpItemsAdapter;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.entity.HelpItem;
import tn.krh.savsamsung.retrofit.INodeJS;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment_help_items_list extends Fragment {

    Context mContext;
    INodeJS myApi;
    @SerializedName("carsList")
    @Expose
    List<HelpItem> helpsList = new ArrayList<>();
    RecyclerView helpsRV;
    public fragment_help_items_list() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_help_items_list, container, false);
        //get data
        String adr = getString(R.string.remote_api_adr_phone);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:1337")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<List<HelpItem>> call = myApi.getAllHelps();
        call.enqueue(new Callback<List<HelpItem>>() {
            @Override
            public void onResponse(Call<List<HelpItem>> call, Response<List<HelpItem>> response) {
                helpsList.clear();
                for(HelpItem item:response.body()){

                    helpsList.add(item);
                    helpItemsAdapter helpsAdapter = new helpItemsAdapter(mContext,helpsList);
                    helpsRV = view.findViewById(R.id.HelpItemsRVList);
                    helpsRV.setHasFixedSize(true);
                    helpsRV.setAdapter(helpsAdapter);
                    helpsRV.setLayoutManager(new GridLayoutManager(mContext,2));
                    //helpsAdapter.notifyDataSetChanged();
                }
                if(response.message().equals("nothing was found")){
                    Toast.makeText(getActivity(),"you have 0 car(s)",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HelpItem>> call, Throwable t) {

                Toast.makeText(getActivity(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
