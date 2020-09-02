package tn.krh.savsamsung.Adapters.service;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.krh.savsamsung.R;
import tn.krh.savsamsung.activities.service.RepairServiceActivity;
import tn.krh.savsamsung.activities.service.ServicesArticle;
import tn.krh.savsamsung.entity.services;
import tn.krh.savsamsung.retrofit.INodeJS;

public class serviceItemsAdapter extends RecyclerView.Adapter<serviceItemsAdapter.ViewHolder> implements Filterable {


    private List<services> services;
    private List<services> services_filtred;
    private SharedPreferences mPreference;
    public static final String sharedPrefFile = "com.krh.app";
            Context mContext;
            INodeJS myApi;
    public serviceItemsAdapter(Context context, List<services> servicess) {
            this.services = servicess;
            mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView typeservice;
        public TextView description;
        public Button serviceBtn;
        public ImageView img;
        public SharedPreferences mPreference;
        public ViewHolder(final View itemView) {
            super(itemView);
            typeservice = itemView.findViewById(R.id.serviceType);
            description = itemView.findViewById(R.id.serviceDesc);
            serviceBtn = itemView.findViewById(R.id.serviceBtn);
            img = itemView.findViewById(R.id.serviceImg);
        }
    }
    //overRide
    @NonNull
    @Override
    public serviceItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View matchView = inflater.inflate(R.layout.service_item_rv, parent, false);
        serviceItemsAdapter.ViewHolder viewHolder = new serviceItemsAdapter.ViewHolder(matchView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull serviceItemsAdapter.ViewHolder holder, int position) {
        final services serviceItem = services.get(position);
        TextView type = holder.typeservice;
        TextView decription = holder.description;
        Button serviceBtn = holder.serviceBtn;
        ImageView img = holder.img;
        //set btn text for service
        if(serviceItem.getType().equals("aide et conseils")){
            serviceBtn.setText("aide et conseils en ligne");
        }
        else if(serviceItem.getType().equals("Réparation et problémes")){
            serviceBtn.setText("demande de réparation");
        }
        else  serviceBtn.setText("afficher plus    >");

        //SET DATA TO ITEMS
        type.setText(serviceItem.getType());
        decription.setText(serviceItem.getDescription());
        //get Image
        String serviceItem_img = serviceItem.getImg();
        //load Img

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        myApi = retrofit.create(INodeJS.class);
        Call<ResponseBody> callImg = myApi.getHelpItemImg(serviceItem_img);
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

        serviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(serviceItem.getType().equals("aide et conseils"))
                {
                    String url = "https://www.samsung.com/ch_fr/support/category/home-appliances/#:~:text=0800%20726%20786Assistance%20technique,les%20smartphones%2C%20tablettes%20et%20wearables.";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    Context context = v.getContext();
                    context.startActivity(i);
                }
                else if(serviceItem.getType().equals("Réparation et problémes"))
                {
                    Context context = v.getContext();
                    v.getContext().startActivity(new Intent(v.getContext(), RepairServiceActivity.class));
                }
                else {
                    mPreference = v.getContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
                    SharedPreferences.Editor Editor = mPreference.edit();
                    Editor.putString("ServiceType",serviceItem.getType());
                    Editor.apply();
                    Context context = v.getContext();
                    v.getContext().startActivity(new Intent(v.getContext(), ServicesArticle.class));
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return services.size();
    }
    @Override
    public Filter getFilter() {
        return filer;
    }
    Filter filer = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<services> filtredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filtredList.addAll(services_filtred);
            }else {
                String filtrePattern = constraint.toString().toLowerCase().trim();
                for (services item:services_filtred){
                    if(item.getType().toLowerCase().contains(filtrePattern)){
                        filtredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            services.clear();
            services.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
